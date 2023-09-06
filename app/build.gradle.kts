plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.secrets.gradle.plugin)
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId ="com.rumosoft.marvelcompose"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/LICENSE*"
        }
    }
    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }

    namespace = "com.rumosoft.marvelcompose"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":feature-characters"))
    implementation(project(":feature-comics"))
    implementation(project(":library-components"))
    implementation(project(":library-commons"))

    implementation(libs.core.ktx)
    implementation(libs.androidx.window)
    implementation(libs.androidx.appcompat)
    implementation(platform(libs.compose.bom))
    implementation(libs.activity.compose)
    implementation(libs.ui)
    implementation(libs.material3)
    implementation(libs.material3.window.size)
    implementation(libs.lifecycle.runtime.ktx)

    implementation(libs.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx)

    implementation(libs.timber)

    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.okhttp)

    implementation(libs.androidx.core.splashscreen)

    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.jupiter)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)

    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.test.manifest)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
