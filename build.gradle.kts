buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(libs.gradle)
        classpath(libs.shot)
        classpath(libs.jacoco.android)
        classpath(libs.secrets.gradle.plugin)
        classpath(libs.spotless.plugin.gradle)
    }
}

plugins {
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.hilt) apply false
}

allprojects {
    plugins.apply("com.diffplug.spotless")
}