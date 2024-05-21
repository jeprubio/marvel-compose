plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    id("shot")
    alias(libs.plugins.kotlin.serialization)
}

android {
    compileSdk = 34

    defaultConfig {
        minSdk = 21
        testOptions.targetSdk = 34

        testApplicationId = "com.rumosoft.marvelcomposetest"
        testInstrumentationRunner = "com.karumi.shot.ShotTestRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
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
            excludes += "META-INF/*"
        }
    }
    testOptions {
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }
    namespace = "com.rumosoft.characters"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":library-marvelapi"))
    implementation(project(":library-components"))

    implementation(libs.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(platform(libs.compose.bom))
    implementation(libs.activity.compose)
    implementation(libs.ui)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)

    implementation(libs.navigation.compose)
    implementation(libs.androidx.navigation.runtime.ktx)

    // Gson + Retrofit (to perform API calls and parse the response)
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okio)
    implementation(libs.okhttp)

    implementation(libs.coil.compose)

    implementation(libs.timber)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)

    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.konsist)
    testImplementation(project(":library-tests"))
    testImplementation(libs.androidx.core.testing)

    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(libs.junitparams)

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}

shot {
    applicationId = "com.rumosoft.marvelcomposeshot"
}

tasks.withType<Test> {
    useJUnitPlatform()
}
