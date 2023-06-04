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
import com.peterchege.pinstagram.core.core_network.util.NetworkResult
import com.peterchege.pinstagram.feature.feature_auth.domain.repository.AuthRepository

import com.peterchege.pinstagram.feature.feature_auth.domain.validation.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpFormState())
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(email = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(password = event.password)
            }
            is RegistrationFormEvent.RepeatedPasswordChanged -> {
                _uiState.value = _uiState.value.copy(repeatedPassword = event.repeatedPassword)
            }
            is RegistrationFormEvent.AcceptTerms -> {
                _uiState.value = _uiState.value.copy(acceptedTerms = event.isAccepted)
            }
            is RegistrationFormEvent.UsernameChanged -> {
                _uiState.value = _uiState.value.copy(username = event.username)
            }
            is RegistrationFormEvent.FullNameChanged -> {
                _uiState.value = _uiState.value.copy(fullName = event.fullName)
            }
            is RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validateEmail(email = _uiState.value.email)
        val passwordResult = validatePassword(password = _uiState.value.password)
        val repeatedPasswordResult = validateRepeatedPassword(
            password= _uiState.value.password,repeatedPassword =  _uiState.value.repeatedPassword
        )
        val termsResult = validateTerms(acceptedTerms = _uiState.value.acceptedTerms)
        val usernameResult = validateUsername(username = _uiState.value.username)
        val fullNameResult = validateFullName(fullName = _uiState.value.fullName)

//        val hasError = listOf(
//            emailResult,
//            passwordResult,
//            repeatedPasswordResult,
//            termsResult,
//            usernameResult,
//            fullNameResult
//        ).any { !it.successful }
//
//        if (hasError) {
//            _uiState.value = _uiState.value.copy(
//                emailError = emailResult.errorMessage,
//                passwordError = passwordResult.errorMessage,
//                repeatedPasswordError = repeatedPasswordResult.errorMessage,
//                termsError = termsResult.errorMessage,
//                usernameError = usernameResult.errorMessage,
//                fullNameError = fullNameResult.errorMessage,
//
//            )
//            return
//        }
        val signUpBody = SignUpBody(
            username = _uiState.value.username,
            fullName = _uiState.value.fullName,
            email = _uiState.value.email,
            password = _uiState.value.password
        )
        viewModelScope.launch {
            val response = authRepository.signUpUser(signUpBody = signUpBody)
            when (response) {
                is NetworkResult.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
                is NetworkResult.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "${response.code} ${response.message}"))
                }
                is NetworkResult.Exception -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "${response.e.message}"))
                }
            }
        }
    }


}



data class SignUpFormState(
    val username:String = "",
    val usernameError:String? = null,

    val fullName:String = "",
    val fullNameError:String? = null,

    val email: String = "",
    val emailError: String? = null,

    val password: String = "",
    val passwordError: String? = null,

    val repeatedPassword: String = "",
    val repeatedPasswordError: String? = null,

    val acceptedTerms: Boolean = false,
    val termsError: String? = null,

    val isLoading :Boolean = false,
)