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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.pinstagram.core.core_common.UiEvent
import com.peterchege.pinstagram.core.core_model.request_models.LoginBody
import com.peterchege.pinstagram.core.core_network.util.NetworkResult
import com.peterchege.pinstagram.feature.feature_auth.domain.repository.AuthRepository
import com.peterchege.pinstagram.feature.feature_auth.domain.validation.LoginFormEvent
import com.peterchege.pinstagram.feature.feature_auth.domain.validation.validateEmail
import com.peterchege.pinstagram.feature.feature_auth.domain.validation.validatePassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginFormState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isLoading:Boolean = false,

)

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository,

) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginFormState())
    val uiState = _uiState.asStateFlow()


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(email = event.email)
            }
            is LoginFormEvent.PasswordChanged -> {
                _uiState.value = uiState.value.copy(password = event.password)
            }
            else -> {

            }
        }
    }

    fun submitData() {
        val emailResult = validateEmail(_uiState.value.email)
        val passwordResult = validatePassword(_uiState.value.password)

        val hasError = listOf(
            emailResult,
            //passwordResult,
        ).any { !it.successful }

        if (hasError) {
            _uiState.value = _uiState.value.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
            )
            return
        }
        _uiState.value = _uiState.value.copy(isLoading = true)
        val loginBody = LoginBody(email = _uiState.value.email, password = _uiState.value.password)

        viewModelScope.launch {
            val response = authRepository.loginUser(loginBody = loginBody)
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