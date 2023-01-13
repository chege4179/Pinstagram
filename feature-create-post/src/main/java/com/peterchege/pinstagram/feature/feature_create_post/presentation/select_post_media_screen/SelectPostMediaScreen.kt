package com.peterchege.pinstagram.feature.feature_create_post.presentation.select_post_media_screen

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
import androidx.navigation.NavHostController
import com.peterchege.compose_image_picker.view.AssetPicker
import com.peterchege.compose_image_picker.constant.AssetPickerConfig
import com.peterchege.compose_image_picker.data.PickerPermissions
import com.peterchege.compose_image_picker.data.toMediaAsset
import com.peterchege.pinstagram.core.core_common.Screens
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SelectPostMediaScreen(
    bottomNavController: NavController,
    navHostController: NavHostController,
    viewModel: SelectPostMediaScreenViewModel = hiltViewModel()

) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize()
    ) {
        val scope = rememberCoroutineScope()
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
                    scope.launch {
                        viewModel.loadSelectedMediaToDatabase(mediaAssets = mediaAssets)
                    }
                    navHostController.navigate(Screens.CONFIRM_POST_MEDIA_SCREEN)

                },
                onClose = {

                },
            )
        }


    }

}