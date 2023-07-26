buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(libs.shot)
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.secrets.gradle.plugin) apply false
    alias(libs.plugins.gradle.versions.plugin) apply true
    alias(libs.plugins.kotlin.jvm) apply false
}
