plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.roborazzi)
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

android {
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        testOptions.targetSdk = libs.versions.android.targetSdk.get().toInt()

        testApplicationId = "com.rumosoft.marvelcomposetest"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

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
            isIncludeAndroidResources = true
        }
    }
    namespace = "com.rumosoft.components"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(platform(libs.compose.bom))
    implementation(libs.activity.compose)
    implementation(libs.ui)
    implementation(libs.material3)
    implementation(libs.material3.window.size)
    implementation(libs.ui.tooling.preview)
    implementation(libs.lifecycle.runtime.ktx)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(libs.lottie.compose)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation3.runtime)

    testImplementation(project(":library-tests"))
    testImplementation(libs.bundles.test.utilities)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.konsist)
    testImplementation(libs.junitparams)
    testImplementation(libs.junit.platform.launcher)
    testImplementation(libs.junit.platform.engine)
    testImplementation(libs.robolectric)
    testImplementation(libs.roborazzi)
    testImplementation(libs.roborazzi.compose)
    testImplementation(libs.roborazzi.junit.rule)
    testImplementation(libs.junit.vintage.engine)
    testImplementation(platform(libs.compose.bom))
    testImplementation(libs.ui.test.junit4)
    testImplementation(libs.ui.tooling)

    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ui.test.junit4)

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}

roborazzi {
    outputDir.set(file("screenshots/roborazzi"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}
