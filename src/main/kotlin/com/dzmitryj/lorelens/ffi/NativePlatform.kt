package com.dzmitryj.lorelens.ffi

enum class NativePlatform(val directory: String, val library: String) {
    WIN_X64("win-x64", "lore.dll"),
    LINUX_X64("linux-x64", "liblore.so"),
    LINUX_ARM64("linux-arm64", "liblore.so"),
    MAC_ARM64("mac-arm64", "liblore.dylib"),
    ;

    companion object {
        fun currentOrNull(
            os: String = System.getProperty("os.name").orEmpty(),
            arch: String = System.getProperty("os.arch").orEmpty(),
        ): NativePlatform? {
            val normalizedOs = os.lowercase()
            val arm = arch.lowercase() in setOf("aarch64", "arm64")

            return when {
                normalizedOs.startsWith("windows") -> if (arm) null else WIN_X64
                normalizedOs.startsWith("mac") || normalizedOs.startsWith("darwin") ->
                    if (arm) MAC_ARM64 else null
                normalizedOs.startsWith("linux") -> if (arm) LINUX_ARM64 else LINUX_X64
                else -> null
            }
        }

        fun describeCurrent(): String =
            "${System.getProperty("os.name")} ${System.getProperty("os.arch")}"
    }
}
