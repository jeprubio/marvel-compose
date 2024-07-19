plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.secrets.gradle.plugin)
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId ="com.rumosoft.marvelcompose"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.rumosoft.marvelcompose.CustomTestRunner"
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
    implementation(project(":library-marvelapi"))

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
    implementation(libs.androidx.ui.tooling.preview.android)

    implementation(libs.glance.widget)
    implementation(libs.glance.material3)

    testImplementation(libs.konsist)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)

    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ui.test.junit4)

    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.android.compiler)

    androidTestImplementation(libs.glance.testing)
    androidTestImplementation(libs.glance.appwidget.testing)

    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    debugImplementation(libs.glance.preview)
    debugImplementation(libs.glance.appwidget.preview)
    debugImplementation(libs.glance.appwidget.host)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
