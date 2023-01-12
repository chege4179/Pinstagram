package com.peterchege.compose_image_picker.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.peterchege.compose_image_picker.constant.AssetPickerConfig
import com.peterchege.compose_image_picker.data.AssetInfo
import com.peterchege.compose_image_picker.data.AssetPickerRepository
import com.peterchege.compose_image_picker.data.AssetPickerRoute
import com.peterchege.compose_image_picker.data.AssetViewModel
import com.peterchege.compose_image_picker.data.AssetViewModelFactory

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AssetPicker(
    assetPickerConfig: AssetPickerConfig,
    onPicked: (List<AssetInfo>) -> Unit,
    onClose: (List<AssetInfo>) -> Unit,
) {
    val context = LocalContext.current
    val navController = rememberAnimatedNavController()
    val viewModel: AssetViewModel = viewModel(
        factory = AssetViewModelFactory(
            assetPickerRepository = AssetPickerRepository(context),
            navController = navController
        )
    )

    LaunchedEffect(Unit, block = {
        viewModel.initDirectories()
    })

    CompositionLocalProvider(LocalAssetConfig provides assetPickerConfig) {
        AssetPickerRoute(
            navController = navController,
            viewModel = viewModel,
            onPicked = onPicked,
            onClose = onClose,
        )
    }
}
