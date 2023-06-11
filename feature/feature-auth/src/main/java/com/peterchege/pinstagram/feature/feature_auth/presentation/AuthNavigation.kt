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
package com.peterchege.pinstagram.feature.feature_auth.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.peterchege.pinstagram.core.core_common.Screens
import com.peterchege.pinstagram.feature.feature_auth.presentation.login_screen.LoginScreen
import com.peterchege.pinstagram.feature.feature_auth.presentation.signup_screen.SignUpScreen


fun NavController.navigateToLoginScreen(){
    this.navigate(route = Screens.LOGIN_SCREEN)
}
fun NavController.navigateToSignUpScreen(){
    this.navigate(route = Screens.SIGN_UP_SCREEN)
}
fun NavController.navigateToDashboard(){
    this.navigate(route = Screens.BOTTOM_TAB_NAVIGATION)
}


fun NavGraphBuilder.loginScreen(
    navigateToSignUpScreen:() -> Unit,
    navigateToDashboard:() -> Unit
){
    composable(route = Screens.LOGIN_SCREEN){
        LoginScreen(
            navigateToSignUpScreen = navigateToSignUpScreen,
            navigateToDashBoard = navigateToDashboard,
        )
    }
}

fun NavGraphBuilder.signUpScreen(
    navigateToLoginScreen: () -> Unit
){
    composable(route = Screens.SIGN_UP_SCREEN){
        SignUpScreen(
            navigateToLoginScreen = navigateToLoginScreen,
        )
    }
}

