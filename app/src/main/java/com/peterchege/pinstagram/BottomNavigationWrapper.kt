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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.peterchege.pinstagram.core.core_common.Screens
import com.peterchege.pinstagram.core.core_model.external_models.BottomNavItem
import com.peterchege.pinstagram.feature.feature_create_post.presentation.createPostScreen
import com.peterchege.pinstagram.feature.feature_feed.presentation.feedScreen
import com.peterchege.pinstagram.feature.feature_feed.presentation.navigateToFeedScreen
import com.peterchege.pinstagram.feature.feature_feed.presentation.navigateToUserProfile
import com.peterchege.pinstagram.feature.feature_notifications.presentation.notificationScreen
import com.peterchege.pinstagram.feature.feature_profile.presentation.authUserProfileScreen
import com.peterchege.pinstagram.feature.feature_search.presentation.presentation.searchScreen


@ExperimentalMaterialApi
@Composable
fun BottomNavBar(
    items:List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick:(BottomNavItem) -> Unit
){
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.DarkGray,
        elevation = 5.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected =selected ,
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.Gray,
                onClick = { onItemClick(item) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription =item.name
                        )
                        if (selected){
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp

                            )
                        }

                    }

                }
            )
        }


    }


}




@ExperimentalMaterialApi
@Composable
fun BottomNavigationWrapper(
    navHostController: NavHostController,
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                items = listOf(
                    BottomNavItem(
                        name="Home",
                        route = Screens.FEED_SCREEN  ,
                        icon = Icons.Default.Home
                    ),
                    BottomNavItem(
                        name="Search",
                        route = Screens.SEARCH_SCREEN   ,
                        icon = Icons.Default.Search
                    ),
                    BottomNavItem(
                        name="Create",
                        route = Screens.FEATURE_CREATE_POST_NAVIGATION  ,
                        icon = Icons.Default.Add
                    ),

                    BottomNavItem(
                        name="Notifications",
                        route = Screens.NOTIFICATION_SCREEN ,
                        icon = Icons.Default.Notifications
                    ),
                    BottomNavItem(
                        name="Profile",
                        route = Screens.LOGGED_IN_USER_PROFILE_SCREEN ,
                        icon = Icons.Default.Person
                    )

                ),
                navController = navController,
                onItemClick ={
                    navController.navigate(it.route)
                }
            )
        }
    ) { innerPadding ->
        // Apply the padding globally to the whole BottomNavScreensController
        Box(modifier = Modifier
            .background(Color.LightGray)
            .padding(innerPadding)
        ) {
            BottomNavigation(navController = navController, navHostController = navHostController)
        }

    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomNavigation(
    navController: NavHostController,
    navHostController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.FEED_SCREEN){

        feedScreen()
        searchScreen(
            navigateToUserProfile = navHostController::navigateToUserProfile
        )
        createPostScreen(
            navController = navController,
            navigateToFeedScreen = navController::navigateToFeedScreen
        )
        notificationScreen()
        authUserProfileScreen()
    }

}


@OptIn(ExperimentalMaterialApi::class)
fun NavGraphBuilder.dashboard(navController:NavHostController){
    composable(route = Screens.BOTTOM_TAB_NAVIGATION){
        BottomNavigationWrapper(navHostController = navController)
    }
}