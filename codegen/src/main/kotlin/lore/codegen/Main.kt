package lore.codegen

import java.io.File

const val DROPPED_FIELDS_FILE = "dropped-fields.txt"

fun main(args: Array<String>) {
    require(args.size >= 4) { "usage: codegen <lore.h> <error.rs> <outputDir> <probeDir>" }

    val header = HeaderParser.parse(File(args[0]).readText())
    val types = TypeMapper(header)
    val errorCodes = ErrorCodeParser.parse(File(args[1]).readText())
    val output = File(args[2])

    output.mkdirs()
    output.listFiles()?.forEach { it.delete() }

    val events = EventEmitter(header, types)

    val files = mapOf(
        "LoreEnums.kt" to EnumEmitter(header).emit(),
        "LoreLayouts.kt" to LayoutEmitter(header, types).emit(),
        "LoreFunctions.kt" to FunctionEmitter(header, types).emit(),
        "LoreStatus.kt" to ErrorCodeEmitter(header.interfaceVersion, errorCodes).emit(),
        "LoreEvents.kt" to events.emit(),
        "LoreBuildInfo.kt" to buildInfo(header),
    )

    files.forEach { (name, content) ->
        output.resolve(name).writeText(content)
        println("$name  ${content.lines().size} lines")
    }

    // Skipped fields are invisible in the generated code, and twice now one has
    // silently cost real data. Writing them out makes the set reviewable, and a
    // test pins it so a new skip has to be looked at.
    output.resolve(DROPPED_FIELDS_FILE).writeText(
        events.droppedFields.joinToString(separator = "\n", postfix = "\n"),
    )
    println("$DROPPED_FIELDS_FILE  ${events.droppedFields.size} fields")

    val probeDir = File(args[3]).also { it.mkdirs() }
    val probe = probeDir.resolve("layout_probe.c")
    probe.writeText(LayoutProbeEmitter(header, types).emit())
    println("${probe.name}  ${probe.readLines().size} lines")

    println("generated bindings for lore ${header.interfaceVersion} into ${output.path}")
}

private fun buildInfo(header: CHeader): String = """
    |${banner(header.interfaceVersion)}
    |package $GENERATED_PACKAGE
    |
    |object LoreBuildInfo {
    |    /** The LORE_INTERFACE_VERSION these bindings were generated against. */
    |    const val INTERFACE_VERSION: String = "${header.interfaceVersion}"
    |}
    |
""".trimMargin()
