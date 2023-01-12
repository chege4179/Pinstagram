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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.peterchege.pinstagram.feature.feature_auth.domain.validation.RegistrationFormEvent


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignUpScreen(
    navController: NavController,

) {
    val signUpScreenViewModel = viewModel<SignUpScreenViewModel>()
    val state = signUpScreenViewModel.state
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        signUpScreenViewModel.validationEvents.collect { event ->
            when (event) {
                is SignUpScreenViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Registration successful",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = state.email,
                onValueChange = {
                    signUpScreenViewModel.onEvent(RegistrationFormEvent.EmailChanged(it))
                },
                isError = state.emailError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Email")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )
            if (state.emailError != null) {
                Text(
                    text = state.emailError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = state.password,
                onValueChange = {
                    signUpScreenViewModel.onEvent(RegistrationFormEvent.PasswordChanged(it))
                },
                isError = state.passwordError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Password")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            if (state.passwordError != null) {
                Text(
                    text = state.passwordError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = state.repeatedPassword,
                onValueChange = {
                    signUpScreenViewModel.onEvent(RegistrationFormEvent.RepeatedPasswordChanged(it))
                },
                isError = state.repeatedPasswordError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Repeat password")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            if (state.repeatedPasswordError != null) {
                Text(
                    text = state.repeatedPasswordError,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = state.acceptedTerms,
                    onCheckedChange = {
                        signUpScreenViewModel.onEvent(RegistrationFormEvent.AcceptTerms(it))
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Accept terms")
            }
            if (state.termsError != null) {
                Text(
                    text = state.termsError,
                    color = MaterialTheme.colors.error,
                )
            }

            Button(
                onClick = {
                    signUpScreenViewModel.onEvent(RegistrationFormEvent.Submit)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Submit")
            }
        }

    }

}