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
package com.peterchege.pinstagram.feature.feature_profile.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.peterchege.pinstagram.core.core_common.Screens
import com.peterchege.pinstagram.feature.feature_profile.presentation.auth_user_profile.AuthUserProfileScreen

fun NavController.navigateToProfileList(postId:String){
    this.navigate(route = Screens.PROFILE_LIST_SCREEN + "/${postId}")
}

fun NavController.navigateToAuthUserProfile(){
    this.navigate(route = Screens.LOGGED_IN_USER_PROFILE_SCREEN)


}

@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.authUserProfileScreen(){
    composable(route = Screens.LOGGED_IN_USER_PROFILE_SCREEN){
        AuthUserProfileScreen()
    }
}