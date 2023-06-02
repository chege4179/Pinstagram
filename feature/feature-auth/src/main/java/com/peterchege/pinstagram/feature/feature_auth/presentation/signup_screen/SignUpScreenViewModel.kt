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

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.pinstagram.core.core_common.Resource
import com.peterchege.pinstagram.core.core_common.Screens
import com.peterchege.pinstagram.core.core_common.UiEvent
import com.peterchege.pinstagram.core.core_model.request_models.SignUpBody
import com.peterchege.pinstagram.feature.feature_auth.domain.use_case.SignUpUseCase
import com.peterchege.pinstagram.feature.feature_auth.domain.validation.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpScreenViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
) : ViewModel() {

    var state by mutableStateOf(RegistrationFormState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading
    fun onEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationFormEvent.RepeatedPasswordChanged -> {
                state = state.copy(repeatedPassword = event.repeatedPassword)
            }
            is RegistrationFormEvent.AcceptTerms -> {
                state = state.copy(acceptedTerms = event.isAccepted)
            }
            is RegistrationFormEvent.UsernameChanged -> {
                state = state.copy(username = event.username)
            }
            is RegistrationFormEvent.FullNameChanged -> {
                state = state.copy(fullName = event.fullName)
            }
            is RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail(email = state.email)
        val passwordResult = validatePassword(password = state.password)
        val repeatedPasswordResult = validateRepeatedPassword(
            password= state.password,repeatedPassword =  state.repeatedPassword
        )
        val termsResult = validateTerms(acceptedTerms = state.acceptedTerms)
        val usernameResult = validateUsername(username = state.username)
        val fullNameResult = validateFullName(fullName = state.fullName)

        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatedPasswordResult,
            termsResult,
            usernameResult,
            fullNameResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordResult.errorMessage,
                termsError = termsResult.errorMessage,
                usernameError = usernameResult.errorMessage,
                fullNameError = fullNameResult.errorMessage,

            )
            return
        }
        val signUpBody = SignUpBody(
            username = state.username,
            fullName = state.fullName,
            email = state.email,
            password = state.password
        )
        signUpUseCase(signUpBody = signUpBody).onEach { result ->
            when(result){
                is Resource.Success -> {
                    _isLoading.value = false
                    _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "Account created successfully"))
                    _eventFlow.emit(UiEvent.Navigate(route = Screens.LOGIN_SCREEN))

                }
                is Resource.Loading -> {
                    _isLoading.value = true

                }
                is Resource.Error -> {
                    _isLoading.value = false
                    _eventFlow.emit(UiEvent.ShowSnackbar(uiText = result.message ?: "An unexpected error occurred "))
                }

            }
        }.launchIn(viewModelScope)
    }


}