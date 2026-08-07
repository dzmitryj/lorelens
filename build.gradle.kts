import lore.FetchLoreNativeTask
import org.jetbrains.intellij.platform.gradle.TestFrameworkType
import org.jetbrains.intellij.platform.gradle.tasks.VerifyPluginTask

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

val pluginDisplayName = "LoreLens"
val pinnedLoreVersion = providers.gradleProperty("loreVersion").get()
val verifyAgainstIde = providers.gradleProperty("verifyAgainstIde").orNull
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
        exclude("lore.h", "error.rs")
    }
}

intellijPlatform {
    pluginConfiguration {
        name = pluginDisplayName

        ideaVersion {
            // 261 is the earliest build on JBR 25, where java.lang.foreign stops
            // being a preview API. Rider 2026.1 is 261.
            sinceBuild = "261"
            untilBuild = "262.*"
        }

        vendor {
            name = "Dimi Mitchell"
            email = "dimi.mitchell@gmail.com"
            url = "https://github.com/dzmitryj/lore-version-control"
        }

        description = """
            Version control integration for <a href="https://lore.org">Lore</a>, Epic Games' open-source
            version control system for repositories that mix code with large binary assets.
            <br><br>
            Lore's status deliberately performs no filesystem walk; it trusts the files it has been told
            changed. This plugin tells it, as you edit, so the Changes view stays accurate without ever
            scanning a large repository.
            <br><br>
            Not affiliated with or endorsed by Epic Games. Bundles the Lore shared library (MIT).
        """.trimIndent()
    }

    pluginVerification {
        // Deprecated usage is excluded deliberately, not to hide problems:
        // VcsFileContent.getContent() is deprecated yet still abstract, with no
        // platform base class implementing it, so every VcsFileRevision must
        // override it -- git4idea included. Everything actionable still fails
        // the build, internal API usage above all.
        failureLevel = listOf(
            VerifyPluginTask.FailureLevel.COMPATIBILITY_PROBLEMS,
            VerifyPluginTask.FailureLevel.INTERNAL_API_USAGES,
            VerifyPluginTask.FailureLevel.NON_EXTENDABLE_API_USAGES,
            VerifyPluginTask.FailureLevel.OVERRIDE_ONLY_API_USAGES,
            VerifyPluginTask.FailureLevel.SCHEDULED_FOR_REMOVAL_API_USAGES,
            VerifyPluginTask.FailureLevel.PLUGIN_STRUCTURE_WARNINGS,
            VerifyPluginTask.FailureLevel.MISSING_DEPENDENCIES,
            VerifyPluginTask.FailureLevel.INVALID_PLUGIN,
        )

        ides {
            recommended()
            // The plugin compiles against 2026.2 but claims 261, so check an
            // installed IDE at the low end of the range:
            //   ./gradlew verifyPlugin -PverifyAgainstIde=<path to IDE>
            verifyAgainstIde?.let { local(it) }
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
