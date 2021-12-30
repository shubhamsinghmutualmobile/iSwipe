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
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        kotlinCompilerExtensionVersion = AndroidPluginVersions.compose
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
    }

    // Because of this error: https://github.com/Kotlin/kotlinx.coroutines/issues/2023
    packagingOptions {
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(files("libs/YouTubeAndroidPlayerApi.jar"))
    AndroidLibs.implementations.forEach(::implementation)
    AndroidLibs.testImplementations.forEach(::testImplementation)
    AndroidLibs.androidTestImplementations.forEach(::androidTestImplementation)
}
