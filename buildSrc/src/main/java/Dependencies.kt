object AndroidPluginVersions {
    const val material = "1.4.0"
    const val appCompat = "1.4.0"
    const val constraintLayout = "2.1.2"
    const val compose = "1.1.0-rc01"
    const val composeActivity = "1.4.0"
    const val composeNavigation = "2.4.0"
    const val composeMaterial3 = "1.0.0-alpha02"
    const val googleAccompanist = "0.22.0-rc"
    const val koin = CommonMainPluginVersions.koin
    const val ktor = CommonMainPluginVersions.ktor
    const val turbine = "0.7.0"
    const val kotlin = "1.6.0"
    const val splashScreen = "1.0.0-alpha02"
    const val coil = "1.4.0"
    const val exoPlayer = "2.16.1"
}

object AndroidLibs {
    val implementations = listOf(
        "com.google.android.material:material:${AndroidPluginVersions.material}",
        "androidx.appcompat:appcompat:${AndroidPluginVersions.appCompat}",
        "androidx.constraintlayout:constraintlayout:${AndroidPluginVersions.constraintLayout}",
        "androidx.activity:activity-compose:${AndroidPluginVersions.composeActivity}",
        "androidx.compose.animation:animation:${AndroidPluginVersions.compose}",
        "androidx.compose.ui:ui-tooling:${AndroidPluginVersions.compose}",
        "androidx.lifecycle:lifecycle-viewmodel-compose:${AndroidPluginVersions.composeNavigation}",
        "androidx.compose.material3:material3:${AndroidPluginVersions.composeMaterial3}",
        "com.google.accompanist:accompanist-pager:${AndroidPluginVersions.googleAccompanist}",
        "com.google.accompanist:accompanist-pager-indicators:${AndroidPluginVersions.googleAccompanist}",
        "com.google.accompanist:accompanist-insets:${AndroidPluginVersions.googleAccompanist}",
        "com.google.accompanist:accompanist-insets-ui:${AndroidPluginVersions.googleAccompanist}",
        "com.google.accompanist:accompanist-systemuicontroller:${AndroidPluginVersions.googleAccompanist}",
        "com.google.accompanist:accompanist-swiperefresh:${AndroidPluginVersions.googleAccompanist}",
        "io.insert-koin:koin-android:${AndroidPluginVersions.koin}",
        "io.insert-koin:koin-androidx-compose:${AndroidPluginVersions.koin}",
        "androidx.core:core-splashscreen:${AndroidPluginVersions.splashScreen}",
        "io.coil-kt:coil-compose:${AndroidPluginVersions.coil}",
        "com.google.android.exoplayer:exoplayer:${AndroidPluginVersions.exoPlayer}"
    )
    val androidTestImplementations = listOf(
        "androidx.compose.ui:ui-test-junit4:${AndroidPluginVersions.compose}",
        "io.insert-koin:koin-test:${AndroidPluginVersions.koin}",
        "io.insert-koin:koin-test-junit4:${AndroidPluginVersions.koin}",
        "io.ktor:ktor-client-mock:${AndroidPluginVersions.ktor}",
    )
    val testImplementations = listOf(
        "app.cash.turbine:turbine:${AndroidPluginVersions.turbine}",
        "io.insert-koin:koin-test:${AndroidPluginVersions.koin}",
        "io.insert-koin:koin-test-junit4:${AndroidPluginVersions.koin}",
        "io.ktor:ktor-client-mock:${AndroidPluginVersions.ktor}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${AndroidPluginVersions.kotlin}",
    )
}

object CommonMainPluginVersions {
    const val ktor = "1.6.7"
    const val koin = "3.1.4"
}

object CommonMainDependencies {
    val implementations = listOf(
        "io.ktor:ktor-client-core:${CommonMainPluginVersions.ktor}",
        "io.ktor:ktor-client-serialization:${CommonMainPluginVersions.ktor}",
        "io.ktor:ktor-client-cio:${CommonMainPluginVersions.ktor}",
        "io.ktor:ktor-client-mock:${CommonMainPluginVersions.ktor}",
        "io.insert-koin:koin-core:${CommonMainPluginVersions.koin}",
    )
}

object AndroidMainVersions {
    const val ktor = CommonMainPluginVersions.ktor
}

object AndroidMainDependencies {
    val implementations = listOf(
        "io.ktor:ktor-client-android:${AndroidMainVersions.ktor}",
    )
}

object IOSMainVersions {
    const val ktor = CommonMainPluginVersions.ktor
}

object IOSMainDependencies {
    val implementations = listOf(
        "io.ktor:ktor-client-ios:${IOSMainVersions.ktor}",
    )
}

object CommonTestVersions {
    const val koin = CommonMainPluginVersions.koin
}

object CommonTestDependencies {
    val implementations = listOf(
        "io.insert-koin:koin-test:${CommonTestVersions.koin}",
        "io.insert-koin:koin-test-junit4:${CommonTestVersions.koin}",
    )
}
