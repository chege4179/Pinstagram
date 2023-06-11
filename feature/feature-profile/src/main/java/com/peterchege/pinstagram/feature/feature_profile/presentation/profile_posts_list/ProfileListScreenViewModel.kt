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
package com.peterchege.pinstagram.feature.feature_profile.presentation.profile_posts_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.pinstagram.core.core_common.Resource
import com.peterchege.pinstagram.core.core_datastore.repository.UserDataStoreRepository
import com.peterchege.pinstagram.core.core_model.external_models.User
import com.peterchege.pinstagram.core.core_model.response_models.GetUserByIdResponse
import com.peterchege.pinstagram.core.core_model.response_models.Post
import com.peterchege.pinstagram.core.core_network.util.NetworkResult
import com.peterchege.pinstagram.feature.feature_profile.domain.use_cases.GetUserProfileUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProfilePostListScreenUiState {
    object Loading : ProfilePostListScreenUiState
    data class Error(val message: String) :ProfilePostListScreenUiState
    data class Success(
        val posts: List<Post>,
    ):ProfilePostListScreenUiState
}



@HiltViewModel
class ProfileListScreenViewModel @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository,
    private val getUserProfile: GetUserProfileUseCase,
    private val savedStateHandle: SavedStateHandle
):ViewModel() {


    private val _uiState = MutableStateFlow<ProfilePostListScreenUiState>(ProfilePostListScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _postId = mutableStateOf("")
    val postId: State<String> = _postId

    val authUser = userDataStoreRepository.getLoggedInUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )


    init {
        savedStateHandle.get<String>("postId")?.let {
            _postId.value = it
        }

    }
    suspend fun getLoggedInUserProfile(userId:String): NetworkResult<GetUserByIdResponse> {
        return getUserProfile(userId = userId)

    }
}