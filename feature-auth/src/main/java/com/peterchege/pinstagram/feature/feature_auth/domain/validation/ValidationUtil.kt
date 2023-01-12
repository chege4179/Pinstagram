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
package com.peterchege.pinstagram.feature.feature_auth.domain.validation

import android.util.Patterns

fun validateEmail(email: String): ValidationResult {
    if(email.isBlank()) {
        return ValidationResult(
            successful = false,
            errorMessage = "The email can't be blank"
        )
    }
    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return ValidationResult(
            successful = false,
            errorMessage = "That's not a valid email"
        )
    }
    return ValidationResult(
        successful = true
    )
}

fun validatePassword(password: String): ValidationResult {
    if(password.length < 8) {
        return ValidationResult(
            successful = false,
            errorMessage = "The password needs to consist of at least 8 characters"
        )
    }
    val containsLettersAndDigits = password.any { it.isDigit() } &&
            password.any { it.isLetter() }
    if(!containsLettersAndDigits) {
        return ValidationResult(
            successful = false,
            errorMessage = "The password needs to contain at least one letter and digit"
        )
    }
    return ValidationResult(
        successful = true
    )
}


fun validateRepeatedPassword(password: String, repeatedPassword: String): ValidationResult {
    if(password != repeatedPassword) {
        return ValidationResult(
            successful = false,
            errorMessage = "The passwords don't match"
        )
    }
    return ValidationResult(
        successful = true
    )
}

fun validateTerms(acceptedTerms: Boolean): ValidationResult {
    if(!acceptedTerms) {
        return ValidationResult(
            successful = false,
            errorMessage = "Please accept the terms"
        )
    }
    return ValidationResult(
        successful = true
    )
}
