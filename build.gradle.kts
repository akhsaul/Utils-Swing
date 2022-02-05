plugins {
    kotlin("jvm")
    java
}

group = "me.akhsaul"
version = "1.0"

dependencies {
    api(project(":Utils-Common"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:${property("coroutine.version")}")
    implementation("com.formdev:flatlaf-intellij-themes:${property("flatlaf.version")}")
    implementation("com.formdev:flatlaf-extras:${property("flatlaf.version")}")
    implementation("com.formdev:svgSalamander:1.1.3")
    implementation("com.formdev:flatlaf:${property("flatlaf.version")}")
    implementation("com.formdev:flatlaf-swingx:${property("flatlaf.version")}")
    implementation("com.teamdev.jxbrowser:jxbrowser-cross-platform:${property("jxbrowser.version")}")
    implementation("com.teamdev.jxbrowser:jxbrowser-javafx:${property("jxbrowser.version")}")
    implementation("com.teamdev.jxbrowser:jxbrowser-swing:${property("jxbrowser.version")}")
    implementation("com.teamdev.jxbrowser:jxbrowser-swt:${property("jxbrowser.version")}")
}