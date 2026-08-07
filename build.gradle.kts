import lore.FetchLoreNativeTask
import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.changelog")
    id("org.jetbrains.intellij.platform")
}

kotlin {
    jvmToolchain(25)
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

val pluginDisplayName = "Lore Version Control"
val pinnedLoreVersion = providers.gradleProperty("loreVersion").get()
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
    jvmArgs("--enable-native-access=ALL-UNNAMED")
}

// Shipped as plain files under <plugin>/native rather than inside the jar, so
// the runtime can dlopen them directly instead of unpacking a copy first.
tasks.prepareSandbox {
    dependsOn(fetchLoreNative)
    from(loreNativeDir) {
        into("${project.name}/native")
        exclude("lore.h")
    }
}

intellijPlatform {
    pluginConfiguration {
        name = pluginDisplayName

        ideaVersion {
            sinceBuild = "262"
            untilBuild = "262.*"
        }

        vendor {
            name = "Dimi Mitchell"
            email = "dimi.mitchell@gmail.com"
        }
    }

    pluginVerification {
        ides {
            recommended()
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
