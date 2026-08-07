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

val pinnedLoreVersion = providers.gradleProperty("loreVersion").get()
val loreNativeDir = layout.buildDirectory.dir("lore-native/$pinnedLoreVersion")

val fetchLoreNative = tasks.register<FetchLoreNativeTask>("fetchLoreNative") {
    loreVersion = pinnedLoreVersion
    manifest = layout.projectDirectory.file("native/lore-versions.json")
    githubToken = providers.environmentVariable("GITHUB_TOKEN")
    downloadCache = gradle.gradleUserHomeDir.resolve("lore-native/$pinnedLoreVersion")
    outputDirectory = loreNativeDir
}

intellijPlatform {
    pluginConfiguration {
        name = "Lore Version Control"

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
