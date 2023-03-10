/*
 * Copyright 2023 PInstagram
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peterchege.pinstagram.feature.feature_auth.presentation.login_screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.peterchege.pinstagram.core.core_common.Screens
import com.peterchege.pinstagram.core.core_common.TestTags
import com.peterchege.pinstagram.feature.feature_auth.di.FeatureAuthModule
import com.peterchege.pinstagram.feature.feature_auth.presentation.login_screen.LoginScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(FeatureAuthModule::class)
class LoginScreenTest{

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Before
    fun setUp(){
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            LoginScreen(navController = navController)
        }
    }

    @Test
    fun Assert_that_the_Login_Fields_are_visible (){
        composeRule.onNodeWithTag(TestTags.LOGIN_EMAIL_FIELD).assertExists()
        composeRule.onNodeWithTag(TestTags.LOGIN_PASSWORD_FIELD).assertExists()
        composeRule.onNodeWithTag(TestTags.LOGIN_CIRCULAR_INDICATOR).assertDoesNotExist()

    }


}