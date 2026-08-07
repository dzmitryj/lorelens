package lore

import org.gradle.api.DefaultTask
import org.gradle.api.file.ArchiveOperations
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.security.MessageDigest
import javax.inject.Inject

@CacheableTask
abstract class FetchLoreNativeTask : DefaultTask() {

    @get:Input
    abstract val loreVersion: Property<String>

    @get:InputFile
    @get:PathSensitive(PathSensitivity.NONE)
    abstract val manifest: RegularFileProperty

    @get:Input
    @get:Optional
    abstract val githubToken: Property<String>

    @get:Internal
    abstract val downloadCache: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    /** loreserver for this host only; used by integration tests, never shipped. */
    @get:Input
    abstract val serverPlatform: Property<String>

    @get:OutputDirectory
    abstract val serverDirectory: DirectoryProperty

    @get:Inject
    abstract val archives: ArchiveOperations

    @get:Inject
    abstract val fs: FileSystemOperations

    @TaskAction
    fun fetch() {
        val release = LoreNativeManifest.read(manifest.get().asFile, loreVersion.get())
        val cache = downloadCache.get().asFile.also { it.mkdirs() }
        val out = outputDirectory.get().asFile

        fs.delete { delete(out) }
        out.mkdirs()

        release.assets.forEach { asset ->
            val archive = fetchArchive(release, cache, asset.file, asset.sha256)
            extract(archive, asset.library, out.resolve(asset.platform))
        }

        writeHeaderOnce(out, release)
        fetchSource(release, cache, out)
        fetchServer(release, cache)
    }

    private fun fetchServer(release: LoreRelease, cache: File) {
        val serverOut = serverDirectory.get().asFile
        fs.delete { delete(serverOut) }
        serverOut.mkdirs()

        val server = release.servers.firstOrNull { it.platform == serverPlatform.get() } ?: run {
            logger.lifecycle("No loreserver for ${serverPlatform.get()}; integration tests will skip")
            return
        }
        val archive = fetchArchive(release, cache, server.file, server.sha256)
        extract(archive, server.binary, serverOut)
    }

    private fun fetchArchive(release: LoreRelease, cache: File, file: String, sha256: String): File {
        val archive = cache.resolve(file)
        if (!archive.isFile || sha256(archive) != sha256) {
            download(release.downloadUrl(file), archive)
        }
        val actual = sha256(archive)
        if (actual != sha256) {
            archive.delete()
            error(
                "Checksum mismatch for $file\n" +
                    "  expected $sha256\n" +
                    "  actual   $actual\n" +
                    "The cached download was deleted. If this repeats, the pinned checksum " +
                    "in the manifest does not match what the release now serves."
            )
        }
        return archive
    }

    // The general library error codes are not exported by lore.h; they live in
    // this Rust file as #[ffi_code(N)] attributes.
    private fun fetchSource(release: LoreRelease, cache: File, out: File) {
        val source = release.errorCodes
        val name = source.path.substringAfterLast('/')
        val cached = cache.resolve(name)

        if (!cached.isFile || sha256(cached) != source.sha256) {
            download(release.sourceUrl(source), cached)
        }

        val actual = sha256(cached)
        if (actual != source.sha256) {
            cached.delete()
            error("Checksum mismatch for ${source.path}: expected ${source.sha256}, got $actual")
        }
        cached.copyTo(out.resolve(name), overwrite = true)
    }

    private fun download(url: String, target: File) {
        logger.lifecycle("Downloading $url")
        val request = HttpRequest.newBuilder(URI.create(url)).GET().apply {
            githubToken.orNull?.takeIf { it.isNotBlank() }?.let { header("Authorization", "Bearer $it") }
        }.build()

        val partial = File(target.absolutePath + ".part")
        partial.delete()

        val client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.NORMAL).build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofFile(partial.toPath()))
        if (response.statusCode() != 200) {
            partial.delete()
            error("Download of $url failed with HTTP ${response.statusCode()}")
        }
        partial.renameTo(target)
    }

    private fun extract(archive: File, binary: String, into: File) {
        val tree = if (archive.name.endsWith(".zip")) {
            archives.zipTree(archive)
        } else {
            archives.tarTree(archives.gzip(archive))
        }

        fs.copy {
            from(tree) {
                include("**/$binary", "**/lore.h", "**/LICENSE.txt", "**/THIRD-PARTY-NOTICES.txt")
                eachFile { path = name }
                includeEmptyDirs = false
            }
            into(into)
        }
    }

    // The header is identical in every archive; hoist one copy so codegen has a
    // single, platform-independent input.
    private fun writeHeaderOnce(out: File, release: LoreRelease) {
        val headers = release.assets.map { out.resolve(it.platform).resolve("lore.h") }
        val missing = headers.filterNot { it.isFile }
        if (missing.isNotEmpty()) {
            error("lore.h missing from: ${missing.joinToString { it.parentFile.name }}")
        }

        val distinct = headers.map { sha256(it) }.distinct()
        if (distinct.size != 1) {
            error("lore.h differs between platform archives of ${release.version}; codegen cannot assume one ABI")
        }

        headers.first().copyTo(out.resolve("lore.h"), overwrite = true)
        headers.forEach { it.delete() }
    }

    private fun sha256(file: File): String {
        val digest = MessageDigest.getInstance("SHA-256")
        file.inputStream().buffered().use { input ->
            val buffer = ByteArray(1 shl 16)
            while (true) {
                val read = input.read(buffer)
                if (read <= 0) break
                digest.update(buffer, 0, read)
            }
        }
        return digest.digest().joinToString("") { "%02x".format(it) }
    }
}
