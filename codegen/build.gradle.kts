plugins {
    id("org.jetbrains.kotlin.jvm")
}

kotlin {
    jvmToolchain(25)
}

dependencies {
    // The root opt-out (kotlin.stdlib.default.dependency=false) is right for the
    // plugin, which gets the stdlib from the platform. This module is a plain
    // JVM application and needs its own.
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

val loreVersion = providers.gradleProperty("loreVersion").get()
val nativeDir = rootProject.layout.buildDirectory.dir("lore-native/$loreVersion").get().asFile
val generatedDir = rootProject.layout.projectDirectory
    .dir("src/main/kotlin/com/dzmitryj/lorevcs/ffi/generated").asFile

tasks.register<JavaExec>("generateLoreBindings") {
    group = "lore"
    description = "Regenerates the FFM bindings from the pinned lore.h. Output is committed."
    dependsOn(":fetchLoreNative")
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass = "lore.codegen.MainKt"
    args(nativeDir.resolve("lore.h").absolutePath, generatedDir.absolutePath)
}
