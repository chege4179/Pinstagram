package com.peterchege.pinstagram.ui.theme



import android.provider.SyncStateContract
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.peterchege.pinstagram.core.core_common.Constants
import com.peterchege.pinstagram.core.core_common.Screens
import com.peterchege.pinstagram.core.core_common.Screens.LOGIN_SCREEN
import com.peterchege.pinstagram.feature.feature_auth.presentation.login_screen.LoginScreen
import com.peterchege.pinstagram.feature.feature_auth.presentation.signup_screen.SignUpScreen

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.LOGIN_SCREEN

    ){
        composable(route = Screens.LOGIN_SCREEN){
            LoginScreen(navController = navController)
        }
        composable(route = Screens.SIGN_UP_SCREEN){
            SignUpScreen(navController = navController)
        }

    }

}