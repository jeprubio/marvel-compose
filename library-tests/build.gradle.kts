plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
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

dependencies {
    api(libs.junit.jupiter)
    api(libs.mockk)
    api(libs.coroutines.test)
    api(libs.mockwebserver)
}
