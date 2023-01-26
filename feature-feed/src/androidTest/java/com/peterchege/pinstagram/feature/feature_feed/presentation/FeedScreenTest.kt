package com.peterchege.pinstagram.feature.feature_feed.presentation

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import com.peterchege.pinstagram.core.core_common.TestTags
import com.peterchege.pinstagram.feature.feature_feed.di.FeatureFeedModule
import com.peterchege.pinstagram.feature.feature_feed.presentation.FeedScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(FeatureFeedModule::class)
class FeedScreenTest{

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp(){
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            val navHostController = rememberNavController()
            FeedScreen(bottomNavController = navController,navHostController = navHostController)
        }
    }

    @Test
    fun Assert_that_the_Feed_container_exists (){
        composeRule.onNodeWithTag(TestTags.FEED_CONTAINER).assertExists()


    }


}