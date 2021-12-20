object PluginVersions {
    const val material = "1.4.0"
    const val appCompat = "1.4.0"
    const val constraintLayout = "2.1.2"
    const val compose = "1.1.0-rc01"
    const val composeActivity = "1.4.0"
    const val composeNavigation = "2.4.0"
    const val composeMaterial3 = "1.0.0-alpha02"
}

object Libs {
    val implementations = listOf(
        "com.google.android.material:material:${PluginVersions.material}",
        "androidx.appcompat:appcompat:${PluginVersions.appCompat}",
        "androidx.constraintlayout:constraintlayout:${PluginVersions.constraintLayout}",
        "androidx.activity:activity-compose:${PluginVersions.composeActivity}",
        "androidx.compose.animation:animation:${PluginVersions.compose}",
        "androidx.compose.ui:ui-tooling:${PluginVersions.compose}",
        "androidx.lifecycle:lifecycle-viewmodel-compose:${PluginVersions.composeNavigation}",
        "androidx.compose.material3:material3:${PluginVersions.composeMaterial3}",
//        "androidx.compose.material:material:${PluginVersions.compose}",
    )
    val androidTestImplementations = listOf(
        "androidx.compose.ui:ui-test-junit4:${PluginVersions.compose}",
    )
}
