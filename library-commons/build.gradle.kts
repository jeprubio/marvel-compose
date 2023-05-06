plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.dicedmelon.gradle.jacoco-android")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.20"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of("17"))
    }
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testApplicationId = "com.rumosoft.marvelcomposetest"
        testInstrumentationRunner = "com.karumi.shot.ShotTestRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "BASE_URL", "\"https://gateway.marvel.com:443/\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    namespace = "com.rumosoft.commons"
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx)

    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okio)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    implementation(libs.timber)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.kotlinx.serialization.json)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
