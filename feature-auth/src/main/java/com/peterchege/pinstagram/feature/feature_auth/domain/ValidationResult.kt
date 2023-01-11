package com.peterchege.pinstagram.feature.feature_auth.domain

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)