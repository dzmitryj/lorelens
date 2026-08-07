plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

// buildSrc is loaded by the Gradle daemon itself, so it targets Gradle's
// minimum JVM rather than the plugin's toolchain.
kotlin {
    jvmToolchain(17)
}
