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
package com.peterchege.pinstagram.feature.feature_profile.presentation.auth_user_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.peterchege.pinstagram.core.core_common.Screens
import com.peterchege.pinstagram.core.core_datastore.repository.UserDataStoreRepository
import com.peterchege.pinstagram.core.core_model.external_models.User
import com.peterchege.pinstagram.core.core_model.response_models.GetUserByIdResponse
import com.peterchege.pinstagram.core.core_model.response_models.Post
import com.peterchege.pinstagram.core.core_network.util.NetworkInfoRepository
import com.peterchege.pinstagram.core.core_network.util.NetworkResult
import com.peterchege.pinstagram.core.core_network.util.NetworkStatus
import com.peterchege.pinstagram.feature.feature_profile.domain.use_cases.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface AuthUserProfileScreenUiState {
    object Loading : AuthUserProfileScreenUiState
    data class Error(val message: String) :AuthUserProfileScreenUiState
    data class Success(
        val posts: List<Post>,
        val user: User,
    ):AuthUserProfileScreenUiState
}


@HiltViewModel
class AuthUserProfileScreenViewModel @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository,
    private val getUserProfile:GetUserProfileUseCase,
    private val networkInfoRepository: NetworkInfoRepository,
):ViewModel() {

    val networkStatus = networkInfoRepository.networkStatus
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue= NetworkStatus.Unknown
        )
    val authUser = userDataStoreRepository.getLoggedInUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    private val _uiState = MutableStateFlow<AuthUserProfileScreenUiState>(AuthUserProfileScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()


    fun loadAuthUserInfo(userId:String){
        viewModelScope.launch {
            val response = getUserProfile(userId = userId)
            when(response){
                is NetworkResult.Exception -> {
                    _uiState.value = AuthUserProfileScreenUiState.Error(message =
                    response.e.message ?: "An exception occurred")
                }
                is NetworkResult.Error -> {
                    _uiState.value = AuthUserProfileScreenUiState.Error(message =
                    response.message ?: "An error occurred")
                }
                is NetworkResult.Success -> {
                    if (response.data.success){
                        _uiState.value = AuthUserProfileScreenUiState.Success(
                            posts = response.data.posts,
                            user = response.data.user
                        )
                    }else{
                        _uiState.value = AuthUserProfileScreenUiState.Error(
                            message = response.data.msg )
                    }

                }
            }
        }

    }


    fun logOutUser(){

        viewModelScope.launch {
            userDataStoreRepository.unsetLoggedInUser()
        }
    }
}