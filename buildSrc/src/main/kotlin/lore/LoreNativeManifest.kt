package lore

import groovy.json.JsonSlurper
import java.io.File

data class LoreAsset(
    val platform: String,
    val file: String,
    val sha256: String,
    val library: String,
)

data class LoreRelease(
    val version: String,
    val interfaceVersion: String,
    val assets: List<LoreAsset>,
) {
    fun downloadUrl(asset: LoreAsset): String =
        "https://github.com/EpicGames/lore/releases/download/$version/${asset.file}"
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

        return LoreRelease(
            version = version,
            interfaceVersion = entry["interfaceVersion"] as String,
            assets = assets.sortedBy { it.platform },
        )
    }
}
