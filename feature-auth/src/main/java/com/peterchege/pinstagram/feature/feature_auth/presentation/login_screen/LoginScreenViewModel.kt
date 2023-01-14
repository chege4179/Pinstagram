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

import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.peterchege.pinstagram.core.core_common.Resource
import com.peterchege.pinstagram.core.core_common.Screens
import com.peterchege.pinstagram.core.core_model.request_models.LoginBody
import com.peterchege.pinstagram.feature.feature_auth.domain.use_case.LoginUseCase
import com.peterchege.pinstagram.feature.feature_auth.domain.validation.*
import com.peterchege.pinstagram.feature.feature_auth.presentation.signup_screen.RegistrationFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,

) : ViewModel() {

    var state by mutableStateOf(LoginFormState())

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is LoginFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            else -> {}
        }
    }

    fun submitData( navController: NavController,scaffoldState: ScaffoldState) {
        val emailResult = validateEmail(state.email)
        val passwordResult = validatePassword(state.password)


        val hasError = listOf(
            emailResult,
            //passwordResult,
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
            )
            return
        }
        val loginBody = LoginBody(email = state.email, password = state.password)
        Log.e("view model","here")
        loginUseCase(loginUser = loginBody).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.e("success","success")
                    _isLoading.value = false
                    navController.navigate(Screens.BOTTOM_TAB_NAVIGATION)
//                    result.message?.let {
//                        scaffoldState.snackbarHostState.showSnackbar(
//                            message = it
//                        )
//                    }

                }
                is Resource.Error -> {
                    Log.e("error","error")
                    _isLoading.value = false
                    result.message?.let {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = it
                        )
                    }
                }
                is Resource.Loading -> {
                    Log.e("loading","loading")
                    _isLoading.value = true
                    result.message?.let {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = it
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}