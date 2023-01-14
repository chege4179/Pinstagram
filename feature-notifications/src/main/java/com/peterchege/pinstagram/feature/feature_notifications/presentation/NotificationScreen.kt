package com.peterchege.pinstagram.feature.feature_notifications.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotificationScreen(
    bottomNavController: NavController,
    navHostController: NavHostController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Notification Screen")

    }

}