package com.peterchege.pinstagram.feature.feature_auth.domain.validation

import org.junit.Assert.*

import org.junit.Test

class ValidationUtilKtTest {

    @Test
    fun validatePasswordTest() {
        val testPassword = "test"
        val result = validatePassword(testPassword)

        assert(!result.successful)
    }

    @Test
    fun validateRepeatedPasswordTest() {
        val password  = "me"
        val repeatPassword = "me"
        val result = validateRepeatedPassword(password,repeatPassword)
        assert(result.successful)
    }

    @Test
    fun validateTermsTest() {
        val result = validateTerms(false)

        assert(!result.successful)
    }
}