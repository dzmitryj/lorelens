package lore.codegen

/**
 * Emits a C program that checks the generated layouts against what the platform
 * compiler actually produces for lore.h. Generated offsets are inferred from
 * field types, and a wrong inference is not an error at runtime -- it is a
 * silently wrong read. This is the only mechanical guard against that.
 *
 * The expected values are compiled into the program, so CI only has to build it
 * and check the exit code.
 */
class LayoutProbeEmitter(private val header: CHeader, private val types: TypeMapper) {

    fun emit(): String = buildString {
        appendLine("/* Generated from lore.h ${header.interfaceVersion} by :codegen. Do not edit. */")
        appendLine("#include <stddef.h>")
        appendLine("#include <stdio.h>")
        appendLine("#include \"lore.h\"")
        appendLine()
        appendLine("static int failures = 0;")
        appendLine()
        appendLine("static void check(const char *what, size_t actual, size_t expected) {")
        appendLine("    if (actual != expected) {")
        appendLine("        printf(\"MISMATCH %s: c=%zu generated=%zu\\n\", what, actual, expected);")
        appendLine("        failures++;")
        appendLine("    }")
        appendLine("}")
        appendLine()
        appendLine("int main(void) {")

        header.structs.filterNot { it.opaque }.sortedBy { it.name }.forEach { struct ->
            val plan = types.layoutOf(struct.fields)
            appendLine("    check(\"${struct.name} sizeof\", sizeof(${struct.name}), ${plan.size});")
            appendLine("    check(\"${struct.name} alignof\", _Alignof(${struct.name}), ${plan.alignment});")
            plan.members
                .filter { it.field.name.isNotEmpty() }
                .forEach { member ->
                    appendLine(
                        "    check(\"${struct.name}.${member.field.name}\", " +
                            "offsetof(${struct.name}, ${member.field.name}), ${member.offset});"
                    )
                }
        }

        appendLine()
        appendLine("    if (failures > 0) {")
        appendLine("        printf(\"%d layout mismatches\\n\", failures);")
        appendLine("        return 1;")
        appendLine("    }")
        appendLine("    printf(\"all layouts match\\n\");")
        appendLine("    return 0;")
        appendLine("}")
    }
}
