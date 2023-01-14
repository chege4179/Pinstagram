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
package com.peterchege.compose_image_picker.data

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.peterchege.compose_image_picker.constant.RequestType
import com.huhx.picker.view.AssetDisplayScreen
import com.peterchege.compose_image_picker.view.AssetPreviewScreen
import com.peterchege.compose_image_picker.view.AssetSelectorScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun AssetPickerRoute(
    navController: NavHostController,
    viewModel: AssetViewModel,
    onPicked: (List<AssetInfo>) -> Unit,
    onClose: (List<AssetInfo>) -> Unit,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = "asset_display"
    ) {
        composable("asset_display") {
            AssetDisplayScreen(
                viewModel = viewModel,
                navigateToDropDown = { navController.navigate("asset_selector?directory=$it") },
                onPicked = onPicked,
                onClose = onClose,
            )
        }

        composable(
            "asset_selector?directory={directory}",
            arguments = listOf(navArgument("directory") { type = NavType.StringType })
        ) { backStackEntry ->
            val arguments = backStackEntry.arguments!!
            val directory = arguments.getString("directory")!!
            AssetSelectorScreen(
                directory = directory,
                assetDirectories = viewModel.directoryGroup,
                navigateUp = { navController.navigateUp() },
                onClick = { name ->
                    navController.navigateUp()
                    viewModel.directory = name
                },
            )
        }

        composable(
            "asset_preview?index={index}&requestType={requestType}",
            arguments = listOf(
                navArgument("index") { type = NavType.IntType },
                navArgument("requestType") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val arguments = backStackEntry.arguments!!
            val index = arguments.getInt("index")
            val requestType = arguments.getString("requestType")
            val assets = viewModel.getAssets(RequestType.valueOf(requestType!!))
            AssetPreviewScreen(
                index = index,
                assets = assets,
                selectedList = viewModel.selectedList,
                navigateUp = { navController.navigateUp() },
            )
        }
    }
}