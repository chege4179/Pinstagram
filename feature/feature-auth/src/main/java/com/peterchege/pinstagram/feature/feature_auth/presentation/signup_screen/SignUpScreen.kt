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
package com.peterchege.pinstagram.feature.feature_auth.presentation.signup_screen

import android.annotation.SuppressLint
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.peterchege.pinstagram.core.core_common.Screens
import com.peterchege.pinstagram.core.core_common.UiEvent
import com.peterchege.pinstagram.feature.feature_auth.domain.validation.RegistrationFormEvent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(
    navigateToLoginScreen:() -> Unit,
    viewModel: SignUpScreenViewModel = hiltViewModel()

) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SignUpScreenContent(
        navigateToLoginScreen = navigateToLoginScreen,
        uiState = uiState,
        eventFlow = viewModel.eventFlow,
        onChangeFullName = { viewModel.onEvent(RegistrationFormEvent.FullNameChanged(it)) },
        onChangeUsername = { viewModel.onEvent(RegistrationFormEvent.UsernameChanged(it)) },
        onChangeEmail = { viewModel.onEvent(RegistrationFormEvent.EmailChanged(it)) },
        onChangePassword = { viewModel.onEvent(RegistrationFormEvent.PasswordChanged(it)) },
        onChangeRepeatedPassword = {
            viewModel.onEvent(
                RegistrationFormEvent.RepeatedPasswordChanged(
                    it
                )
            )
        },
        onSubmit = {
            viewModel.onEvent(
                RegistrationFormEvent.Submit
            )
        },

    )

}


@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignUpScreenContent(
    uiState: SignUpFormState,
    eventFlow: SharedFlow<UiEvent>,
    onChangeFullName: (String) -> Unit,
    onChangeUsername: (String) -> Unit,
    onChangeEmail: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onChangeRepeatedPassword: (String) -> Unit,
    onSubmit: () -> Unit,

    navigateToLoginScreen: () -> Unit,


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

                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = uiState.fullName,
                onValueChange = {
                    onChangeFullName(it)

                },
                isError = uiState.fullNameError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Full Name")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
            if (uiState.fullNameError != null) {
                Text(
                    text = uiState.fullNameError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = uiState.username,
                onValueChange = {
                    onChangeUsername(it)

                },
                isError = uiState.usernameError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Username")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
            if (uiState.usernameError != null) {
                Text(
                    text = uiState.usernameError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = uiState.email,
                onValueChange = {
                    onChangeEmail(it)
                },
                isError = uiState.emailError != null,
                modifier = Modifier.fillMaxWidth(),
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
                modifier = Modifier.fillMaxWidth(),
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
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = uiState.repeatedPassword,
                onValueChange = {
                    onChangeRepeatedPassword(it)

                },
                isError = uiState.repeatedPasswordError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Repeat password")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            if (uiState.repeatedPasswordError != null) {
                Text(
                    text = uiState.repeatedPasswordError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

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
                } else {
                    Text(text = "Sign Up")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                onClick = {
                    navigateToLoginScreen()
                }) {
                Text(text = "Login")
            }
        }

    }

}