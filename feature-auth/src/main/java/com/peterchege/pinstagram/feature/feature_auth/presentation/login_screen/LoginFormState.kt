package com.peterchege.pinstagram.feature.feature_auth.presentation.login_screen

data class LoginFormState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
)