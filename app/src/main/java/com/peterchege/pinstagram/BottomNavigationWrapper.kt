package com.peterchege.pinstagram

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.peterchege.pinstagram.core.core_common.Screens
import com.peterchege.pinstagram.core.core_model.external_models.BottomNavItem
import com.peterchege.pinstagram.feature.feature_feed.presentation.FeedScreen


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
        items.forEach{ item ->
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
                        route = Screens.CREATE_POST_SCREEN   ,
                        icon = Icons.Default.Search
                    ),

                    BottomNavItem(
                        name="Notifications",
                        route = Screens.NOTIFICATION_SCREEN ,
                        icon = Icons.Default.Notifications
                    ),
                    BottomNavItem(
                        name="Profile",
                        route = Screens.PROFILE_SCREEN ,
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



@Composable
fun BottomNavigation(
    navController: NavHostController,
    navHostController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.FEED_SCREEN){

        composable(
            route = Screens.FEED_SCREEN
        ){
            FeedScreen(bottomNavController = navController, navHostController = navHostController)
        }
//        composable(
//            route = Screens.SEARCH_SCREEN
//        ){
//            SearchScreen(bottomNavController = navController, navHostController = navHostController)
//        }
//        composable(
//            route = Screens.CREATE_POST_SCREEN
//        ){
//            CreatePostScreen(bottomNavController = navController, navHostController = navHostController)
//        }
//        composable(
//            route = Screens.NOTIFICATION_SCREEN
//        ){
//            NotificationScreen(navController,navHostController=  navHostController)
//        }
//        composable(
//            route = Screens.PROFILE_SCREEN
//        ){
//            ProfileNavigation(navController, navHostController = navHostController)
//        }

    }

}