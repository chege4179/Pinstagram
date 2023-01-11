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
package com.peterchege.pinstagram.ui.theme



import android.provider.SyncStateContract
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.peterchege.pinstagram.core.core_common.Constants
import com.peterchege.pinstagram.core.core_common.Screens
import com.peterchege.pinstagram.core.core_common.Screens.LOGIN_SCREEN
import com.peterchege.pinstagram.feature.feature_auth.presentation.login_screen.LoginScreen
import com.peterchege.pinstagram.feature.feature_auth.presentation.signup_screen.SignUpScreen

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.LOGIN_SCREEN

    ){
        composable(route = Screens.LOGIN_SCREEN){
            LoginScreen(navController = navController)
        }
        composable(route = Screens.SIGN_UP_SCREEN){
            SignUpScreen(navController = navController)
        }

    }

}