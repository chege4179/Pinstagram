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