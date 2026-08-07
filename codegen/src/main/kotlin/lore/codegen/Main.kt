package lore.codegen

import java.io.File

fun main(args: Array<String>) {
    require(args.size >= 2) { "usage: codegen <lore.h> <outputDir>" }

    val header = HeaderParser.parse(File(args[0]).readText())
    val types = TypeMapper(header)
    val output = File(args[1])

    output.mkdirs()
    output.listFiles()?.forEach { it.delete() }

    val files = mapOf(
        "LoreEnums.kt" to EnumEmitter(header).emit(),
        "LoreLayouts.kt" to LayoutEmitter(header, types).emit(),
        "LoreFunctions.kt" to FunctionEmitter(header, types).emit(),
    )

    files.forEach { (name, content) ->
        output.resolve(name).writeText(content)
        println("$name  ${content.lines().size} lines")
    }

    println("generated bindings for lore ${header.interfaceVersion} into ${output.path}")
}
