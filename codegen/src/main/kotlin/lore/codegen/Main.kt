package lore.codegen

import java.io.File

fun main(args: Array<String>) {
    require(args.size >= 1) { "usage: codegen <lore.h> [error.rs] [outputDir]" }

    val header = HeaderParser.parse(File(args[0]).readText())

    println("interfaceVersion = ${header.interfaceVersion}")
    println("defines   = ${header.defines.size}")
    println("enums     = ${header.enums.size}")
    println("structs   = ${header.structs.size} (opaque ${header.structs.count { it.opaque }})")
    println("aliases   = ${header.aliases.size}")
    println("functions = ${header.functions.size}")
}
