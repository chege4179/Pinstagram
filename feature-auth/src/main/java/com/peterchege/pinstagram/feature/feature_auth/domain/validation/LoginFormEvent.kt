package com.peterchege.pinstagram.feature.feature_auth.domain.validation

sealed class LoginFormEvent {
    data class EmailChanged(val email: String) : LoginFormEvent()
    data class PasswordChanged(val password: String) : LoginFormEvent()


    object Submit: LoginFormEvent()
}