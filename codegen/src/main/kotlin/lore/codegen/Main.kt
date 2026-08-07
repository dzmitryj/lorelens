package lore.codegen

import java.io.File

fun main(args: Array<String>) {
    require(args.size >= 3) { "usage: codegen <lore.h> <error.rs> <outputDir>" }

    val header = HeaderParser.parse(File(args[0]).readText())
    val types = TypeMapper(header)
    val errorCodes = ErrorCodeParser.parse(File(args[1]).readText())
    val output = File(args[2])

    output.mkdirs()
    output.listFiles()?.forEach { it.delete() }

    val files = mapOf(
        "LoreEnums.kt" to EnumEmitter(header).emit(),
        "LoreLayouts.kt" to LayoutEmitter(header, types).emit(),
        "LoreFunctions.kt" to FunctionEmitter(header, types).emit(),
        "LoreStatus.kt" to ErrorCodeEmitter(header.interfaceVersion, errorCodes).emit(),
        "LoreEvents.kt" to EventEmitter(header, types).emit(),
    )

    files.forEach { (name, content) ->
        output.resolve(name).writeText(content)
        println("$name  ${content.lines().size} lines")
    }

    println("generated bindings for lore ${header.interfaceVersion} into ${output.path}")
}
