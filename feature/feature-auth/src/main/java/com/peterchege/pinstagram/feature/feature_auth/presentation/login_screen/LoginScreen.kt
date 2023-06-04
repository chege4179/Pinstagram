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
package com.peterchege.pinstagram.feature.feature_auth.presentation.login_screen

//import androidx.hilt.navigation.compose.hiltViewModel
import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.peterchege.pinstagram.core.core_common.Screens
import com.peterchege.pinstagram.core.core_common.TestTags
import com.peterchege.pinstagram.core.core_common.UiEvent
import com.peterchege.pinstagram.feature.feature_auth.domain.validation.LoginFormEvent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreenContent(
        eventFlow = viewModel.eventFlow,
        uiState = uiState,
        onChangeEmail = { viewModel.onEvent(LoginFormEvent.EmailChanged(it)) },
        onChangePassword = { viewModel.onEvent(LoginFormEvent.PasswordChanged(it)) },
        onSubmit = { viewModel.submitData() },
        navController = navController
    )

}


@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreenContent(
    eventFlow:SharedFlow<UiEvent>,
    uiState:LoginFormState,
    onChangeEmail:(String) -> Unit,
    onChangePassword:(String) -> Unit,
    onSubmit:() -> Unit,
    navController: NavController,
) {
    val scaffoldState = rememberScaffoldState()
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText
                    )
                }
                is UiEvent.Navigate -> {
                    navController.navigate(route = event.route)
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier= Modifier
                .fillMaxSize()
                .padding(20.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Pinstagram App",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
                TextField(
                    value = uiState.email,
                    onValueChange = {
                        onChangeEmail(it)
                    },
                    isError = uiState.emailError != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(TestTags.LOGIN_EMAIL_FIELD)
                    ,
                    placeholder = {
                        Text(text = "Email")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    )
                )
                if (uiState.emailError != null) {
                    Text(
                        text = uiState.emailError,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = uiState.password,
                    onValueChange = {
                        onChangePassword(it)
                    },
                    isError = uiState.passwordError != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(TestTags.LOGIN_PASSWORD_FIELD)
                    ,
                    placeholder = {
                        Text(text = "Password")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )
                if (uiState.passwordError != null) {
                    Text(
                        text = uiState.passwordError,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = {
                        keyboardController?.hide()
                        onSubmit()
                    }
                )
                {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(color = Color.White)
                    }else{
                        Text(text = "Login")
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                TextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = {
                        navController.navigate(Screens.SIGN_UP_SCREEN)
                    }) {
                    Text(text = "Sign Up")
                }
            }

        }

    }
}
