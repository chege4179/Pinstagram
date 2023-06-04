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
package com.peterchege.pinstagram.feature.feature_create_post.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.peterchege.pinstagram.core.core_common.Screens
import com.peterchege.pinstagram.feature.feature_create_post.presentation.screens.ConfirmPostMediaScreen
import com.peterchege.pinstagram.feature.feature_create_post.presentation.screens.SelectPostMediaScreen


@Composable
fun NavGraphBuilder.FeatureCreatePostNavigation(navController:NavController) {
    val navController = rememberNavController()
    navigation(
        startDestination = Screens.SELECT_POST_MEDIA_SCREEN,
        route = Screens.FEATURE_CREATE_POST_NAVIGATION
    ){

        composable(route = Screens.CONFIRM_POST_MEDIA_SCREEN){ entry ->
            val viewModel = entry.sharedViewModel<CreatePostScreenViewModel>(navController = navController)
            ConfirmPostMediaScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = Screens.SELECT_POST_MEDIA_SCREEN){ entry ->
            val viewModel = entry.sharedViewModel<CreatePostScreenViewModel>(navController = navController)
            SelectPostMediaScreen(bottomNavController = navController, navHostController = navController,viewModel = viewModel)
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}
