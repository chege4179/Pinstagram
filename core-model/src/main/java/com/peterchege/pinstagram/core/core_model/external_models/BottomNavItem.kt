package com.peterchege.pinstagram.core.core_model.external_models

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val name:String,
    val route:String,
    val icon: ImageVector,
    val badgeCount:Int = 0,

    )
