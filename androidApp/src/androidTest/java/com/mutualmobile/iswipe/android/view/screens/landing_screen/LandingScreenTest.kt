package com.mutualmobile.iswipe.android.view.screens.landing_screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mutualmobile.iswipe.android.view.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LandingScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun assert_all_items_are_displayed_correctly() {
        composeTestRule.onNodeWithTag(WeatherTestTag).assertIsDisplayed()
        composeTestRule.onNodeWithTag(YoutubeTestTag).assertIsDisplayed()
    }
}
