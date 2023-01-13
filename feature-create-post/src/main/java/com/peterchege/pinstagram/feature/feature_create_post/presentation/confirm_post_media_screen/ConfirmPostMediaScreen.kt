package com.peterchege.pinstagram.feature.feature_create_post.presentation.confirm_post_media_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ConfirmPostMediaScreen(
    navController: NavController,
    viewModel: ConfirmPostMediaScreenViewModel = hiltViewModel()

) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {


    }

}