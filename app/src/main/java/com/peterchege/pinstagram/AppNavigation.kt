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
package com.peterchege.pinstagram



import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.peterchege.pinstagram.core.core_common.Screens
import com.peterchege.pinstagram.core.core_model.external_models.User
import com.peterchege.pinstagram.feature.feature_auth.presentation.loginScreen
import com.peterchege.pinstagram.feature.feature_auth.presentation.navigateToDashboard
import com.peterchege.pinstagram.feature.feature_auth.presentation.navigateToLoginScreen
import com.peterchege.pinstagram.feature.feature_auth.presentation.navigateToSignUpScreen
import com.peterchege.pinstagram.feature.feature_auth.presentation.signUpScreen
import com.peterchege.pinstagram.feature.feature_comments.presentation.CommentsScreen
import com.peterchege.pinstagram.feature.feature_create_post.presentation.screens.ConfirmPostMediaScreen
import com.peterchege.pinstagram.feature.feature_profile.presentation.profile_posts_list.ProfileListScreen
import com.peterchege.pinstagram.feature.feature_profile.presentation.user_profile.UserProfileScreen




fun getInitialRoute(user: User?) : String {
    return if(user== null){
        Screens.LOGIN_SCREEN
    }else{
        if (user.username == ""){
            Screens.LOGIN_SCREEN
        }else{
            Screens.BOTTOM_TAB_NAVIGATION
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppNavigation(
    navController: NavHostController,
    viewModel: AppNavigationViewModel = hiltViewModel(),

) {
    val user by viewModel.loggedInUser.collectAsStateWithLifecycle()
    NavHost(
        navController = navController,
        startDestination = getInitialRoute(user = user)
    ){
        loginScreen(
            navigateToSignUpScreen = navController::navigateToSignUpScreen,
            navigateToDashboard = navController::navigateToDashboard,
        )
        signUpScreen (
            navigateToLoginScreen = navController::navigateToLoginScreen
        )
        dashboard(navController = navController)

        composable(route = Screens.CONFIRM_POST_MEDIA_SCREEN){
            ConfirmPostMediaScreen()
        }
        composable(route = Screens.COMMENTS_SCREEN + "/{postId}"){
            CommentsScreen(navController = navController)
        }
        composable(route = Screens.PROFILE_LIST_SCREEN + "/{postId}"){
            ProfileListScreen()
        }
        composable(route = Screens.USER_PROFILE_SCREEN + "/{userId}"){
            UserProfileScreen(navController = navController)
        }
    }

}
