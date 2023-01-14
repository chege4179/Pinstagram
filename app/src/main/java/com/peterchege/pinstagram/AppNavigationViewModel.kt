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
package com.peterchege.pinstagram

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.pinstagram.core.core_model.external_models.User
import com.peterchege.pinstagram.feature.feature_auth.data.AuthRepositoryImpl
//import com.peterchege.pinstagram.feature.feature_auth.data.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AppNavigationViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
):ViewModel() {
    val _loggedInUser = mutableStateOf<User?>(null)
    val loggedInUser : State<User?> =_loggedInUser

    init {
        viewModelScope.launch {
            authRepositoryImpl.getLoggedInUser().collectLatest {
                _loggedInUser.value = it
            }
        }
    }
}