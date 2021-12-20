plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.mutualmobile.iswipe.android"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = PluginVersions.compose
    }
}

dependencies {
    implementation(project(":shared"))
    Libs.implementations.forEach(::implementation)
    Libs.androidTestImplementations.forEach(::androidTestImplementation)
}
