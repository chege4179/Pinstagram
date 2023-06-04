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
package com.peterchege.pinstagram.feature.feature_create_post.presentation.screens

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavController
import com.peterchege.compose_image_picker.view.AssetPicker
import com.peterchege.compose_image_picker.constant.AssetPickerConfig
import com.peterchege.compose_image_picker.data.PickerPermissions
import com.peterchege.compose_image_picker.data.toMediaAsset
import com.peterchege.pinstagram.core.core_common.Screens
import com.peterchege.pinstagram.feature.feature_create_post.presentation.CreatePostScreenViewModel
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SelectPostMediaScreen(
    bottomNavController: NavController,
    navHostController: NavController,
    viewModel: CreatePostScreenViewModel = hiltViewModel()

) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize()
    ) {
        PickerPermissions(
            permissions = listOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        ) {
            AssetPicker(
                assetPickerConfig = AssetPickerConfig(gridCount = 3),
                onPicked = { assets ->
                    val mediaAssets = assets.map { it.toMediaAsset() }
                    viewModel.setMediaAssets(mediaAssetsState = mediaAssets)
                    navHostController.navigate(Screens.CONFIRM_POST_MEDIA_SCREEN)

                },
                onClose = {
                    scope.launch {
                        viewModel.clearMediaAssets()
                    }
                    bottomNavController.navigate(Screens.FEED_SCREEN)

                },
            )
        }


    }

}