package lore

import groovy.json.JsonSlurper
import java.io.File

data class LoreAsset(
    val platform: String,
    val file: String,
    val sha256: String,
    val library: String,
)

data class LoreSource(val path: String, val sha256: String)

/** loreserver, used only by integration tests; never shipped in the plugin. */
data class LoreServer(
    val platform: String,
    val file: String,
    val sha256: String,
    val binary: String,
)

data class LoreRelease(
    val version: String,
    val interfaceVersion: String,
    val assets: List<LoreAsset>,
    val servers: List<LoreServer>,
    val errorCodes: LoreSource,
) {
    fun downloadUrl(file: String): String =
        "https://github.com/EpicGames/lore/releases/download/$version/$file"

    fun sourceUrl(source: LoreSource): String =
        "https://raw.githubusercontent.com/EpicGames/lore/$version/${source.path}"
}

object LoreNativeManifest {

    @Suppress("UNCHECKED_CAST")
    fun read(manifest: File, version: String): LoreRelease {
        val root = JsonSlurper().parse(manifest) as Map<String, Any>
        val entry = root[version] as? Map<String, Any>
            ?: error(
                "No entry for lore version '$version' in ${manifest.name}. " +
                    "Known versions: ${root.keys.joinToString()}"
            )

        val assets = (entry["assets"] as Map<String, Map<String, String>>).map { (platform, fields) ->
            LoreAsset(
                platform = platform,
                file = fields.getValue("file"),
                sha256 = fields.getValue("sha256"),
                library = fields.getValue("library"),
            )
        }

        val errorCodes = entry["errorCodes"] as Map<String, String>

        val servers = (entry["server"] as Map<String, Map<String, String>>).map { (platform, fields) ->
            LoreServer(
                platform = platform,
                file = fields.getValue("file"),
                sha256 = fields.getValue("sha256"),
                binary = fields.getValue("binary"),
            )
        }

        return LoreRelease(
            version = version,
            interfaceVersion = entry["interfaceVersion"] as String,
            assets = assets.sortedBy { it.platform },
            servers = servers.sortedBy { it.platform },
            errorCodes = LoreSource(errorCodes.getValue("path"), errorCodes.getValue("sha256")),
        )
    }
}
