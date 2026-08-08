import lore.FetchLoreNativeTask
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.intellij.platform.gradle.tasks.VerifyPluginTask

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.changelog")
    id("org.jetbrains.intellij.platform")
}

kotlin {
    jvmToolchain(25)

    compilerOptions {
        // Without this, implementing a platform Kotlin interface emits a
        // delegating override in our class for every default method it has.
        // Those count as our usages, so a handful of deprecated and
        // experimental platform methods get attributed to code we never wrote.
        freeCompilerArgs.add("-jvm-default=no-compatibility")
    }
}

dependencies {
    testImplementation(libs.junit)

    intellijPlatform {
        intellijIdea("2026.2")
        testFramework(TestFrameworkType.Platform)

        // Pairs with the com.intellij.modules.vcs entry in plugin.xml.
        bundledModule("intellij.platform.vcs.impl")

        pluginVerifier()
        zipSigner()
    }
}

val pluginDisplayName = "LoreLens"
val pinnedLoreVersion = providers.gradleProperty("loreVersion").get()

// <lore version>.<plugin revision>: 0.8.6.1 is the first release against
// liblore v0.8.6. The lore part answers "which library does this bundle"
// without opening the zip; the last part is this plugin's own counter and
// resets when the lore version moves.
version = pinnedLoreVersion.removePrefix("v") + "." + providers.gradleProperty("pluginRevision").get()
val verifyAgainstIde = providers.gradleProperty("verifyAgainstIde").orNull
val loreNativeDir = layout.buildDirectory.dir("lore-native/$pinnedLoreVersion")
val loreServerDir = layout.buildDirectory.dir("lore-server/$pinnedLoreVersion")

val hostPlatform = run {
    val os = System.getProperty("os.name").lowercase()
    val arm = System.getProperty("os.arch").lowercase() in setOf("aarch64", "arm64")
    when {
        os.startsWith("windows") -> "win-x64"
        os.startsWith("mac") -> if (arm) "mac-arm64" else "mac-x64"
        else -> if (arm) "linux-arm64" else "linux-x64"
    }
}

val fetchLoreNative = tasks.register<FetchLoreNativeTask>("fetchLoreNative") {
    loreVersion = pinnedLoreVersion
    manifest = layout.projectDirectory.file("native/lore-versions.json")
    githubToken = providers.environmentVariable("GITHUB_TOKEN")
    downloadCache = gradle.gradleUserHomeDir.resolve("lore-native/$pinnedLoreVersion")
    outputDirectory = loreNativeDir
    serverPlatform = hostPlatform
    serverDirectory = loreServerDir
}

tasks.test {
    dependsOn(fetchLoreNative)
    systemProperty("lore.native.dir", loreNativeDir.get().asFile.absolutePath)
    systemProperty("lore.server.dir", loreServerDir.get().asFile.absolutePath)
    listOf("lore.probe.repo", "lore.probe.file").forEach { key ->
        providers.systemProperty(key).orNull?.let { systemProperty(key, it) }
    }
    jvmArgs("--enable-native-access=ALL-UNNAMED")
}

// Shipped as plain files under <plugin>/native rather than inside the jar, so
// the runtime can dlopen them directly instead of unpacking a copy first.
tasks.prepareSandbox {
    dependsOn(fetchLoreNative)
    from(loreNativeDir) {
        into("${project.name}/native")
        exclude("lore.h", "error.rs")
    }
}

intellijPlatform {
    pluginConfiguration {
        name = pluginDisplayName

        ideaVersion {
            // 261 is the earliest build on JBR 25, where java.lang.foreign stops
            // being a preview API. Rider 2026.1 is 261.
            sinceBuild = "261"
            untilBuild = "262.*"
        }

        vendor {
            name = "Dimi Mitchell"
            email = "dimi.mitchell@gmail.com"
            url = "https://github.com/dzmitryj/lore-version-control"
        }

        description = """
            Version control integration for <a href="https://lore.org">Lore</a>, Epic Games' open-source
            version control system for repositories that mix code with large binary assets.
            <br><br>
            Lore's status deliberately performs no filesystem walk; it trusts the files it has been told
            changed. This plugin tells it, as you edit, so the Changes view stays accurate without ever
            scanning a large repository.
            <br><br>
            <ul>
                <li>Local Changes with staging, locks, and conflict resolution -- merge and revert both</li>
                <li>History: every branch in one graph, branch-owned lanes and colours, merge direction visible</li>
                <li>Branch Graph: the repository as swimlanes, with switch and merge either direction from a right-click</li>
                <li>Blame hints in the editor, revision diffs, file history across renames</li>
                <li>A Lore console with optional native debug logging</li>
            </ul>
            Not affiliated with or endorsed by Epic Games. Bundles the Lore shared library (MIT).
        """.trimIndent()

        changeNotes = """
            <b>0.8.6.1</b> -- first release, against liblore v0.8.6.
            <ul>
                <li>Full merge and revert workflows, both directions, with conflict resolution</li>
                <li>Branch-aware history and branch graph with one colour per branch</li>
                <li>File locks, blame, per-file history following renames</li>
                <li>Historical content read by content address, correct across moves and deletes</li>
            </ul>
        """.trimIndent()
    }

    pluginVerification {
        // Deprecated usage is excluded deliberately, not to hide problems:
        // VcsFileContent.getContent() is deprecated yet still abstract, and no
        // platform base class implements it, so every VcsFileRevision has to
        // override it. Everything actionable still fails the build, internal
        // API usage above all.
        failureLevel = listOf(
            VerifyPluginTask.FailureLevel.COMPATIBILITY_PROBLEMS,
            VerifyPluginTask.FailureLevel.INTERNAL_API_USAGES,
            VerifyPluginTask.FailureLevel.NON_EXTENDABLE_API_USAGES,
            VerifyPluginTask.FailureLevel.OVERRIDE_ONLY_API_USAGES,
            VerifyPluginTask.FailureLevel.SCHEDULED_FOR_REMOVAL_API_USAGES,
            VerifyPluginTask.FailureLevel.PLUGIN_STRUCTURE_WARNINGS,
            VerifyPluginTask.FailureLevel.MISSING_DEPENDENCIES,
            VerifyPluginTask.FailureLevel.INVALID_PLUGIN,
        )

        ides {
            recommended()
            // The plugin compiles against 2026.2 but claims 261, so check an
            // installed IDE at the low end of the range:
            //   ./gradlew verifyPlugin -PverifyAgainstIde=<path to IDE>
            verifyAgainstIde?.let { local(it) }
        }
    }

    signing {
        certificateChain = providers.environmentVariable("CERTIFICATE_CHAIN")
        privateKey = providers.environmentVariable("PRIVATE_KEY")
        password = providers.environmentVariable("PRIVATE_KEY_PASSWORD")
    }

    publishing {
        token = providers.environmentVariable("PUBLISH_TOKEN")
    }
}
