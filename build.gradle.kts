buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.hilt.android.gradle.plugin)
        classpath(libs.shot)
        classpath(libs.jacoco.android)
        classpath(libs.secrets.gradle.plugin)
        classpath(libs.spotless.plugin.gradle)
    }
}

allprojects {
    plugins.apply("com.diffplug.spotless")
}