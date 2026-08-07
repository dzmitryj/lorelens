import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.changelog")
    id("org.jetbrains.intellij.platform")
}

// The plugin binds liblore's C API through java.lang.foreign, which is a preview
// API on JBR 21 (shipped by 2025.3) and final on JBR 25 (shipped by 2026.2).
// Targeting 262 is therefore a hard requirement, not a preference.
kotlin {
    jvmToolchain(25)
}

// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
    testImplementation(libs.junit)

    // IntelliJ Platform Gradle Plugin Dependencies Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
    intellijPlatform {
        intellijIdea("2026.2")
        testFramework(TestFrameworkType.Platform)

        // Content module defining the com.intellij.modules.vcs alias. Required
        // on the classpath alongside the matching <dependencies> entry in
        // plugin.xml -- the two are not redundant. See the commit message.
        bundledModule("intellij.platform.vcs.impl")

        pluginVerifier()
        zipSigner()
    }
}

intellijPlatform {
    pluginConfiguration {
        name = "Lore Version Control"

        ideaVersion {
            // Capped deliberately. The plugin binds a pre-1.0 native ABI and a
            // platform VCS API that is stable but not frozen, so a broken 263
            // must not be able to auto-install onto users. Loosen only after
            // testing against an EAP.
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
