package com.peterchege.pinstagram.feature.feature_search.presentation

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
fun SearchScreen(
    bottomNavController: NavController,
    navHostController: NavHostController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Search Screen")

    }

}