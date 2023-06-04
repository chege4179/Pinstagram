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
package com.peterchege.pinstagram.feature.feature_feed.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.pinstagram.core.core_common.Resource
import com.peterchege.pinstagram.core.core_common.Screens
import com.peterchege.pinstagram.core.core_common.UiEvent
import com.peterchege.pinstagram.core.core_model.response_models.Post
import com.peterchege.pinstagram.core.core_network.util.NetworkResult
import com.peterchege.pinstagram.feature.feature_feed.data.FeedRepositoryImpl
import com.peterchege.pinstagram.feature.feature_feed.domain.repository.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

import javax.inject.Inject


sealed interface FeedScreenUiState {
    object Loading : FeedScreenUiState
    data class Error(val message: String) :FeedScreenUiState
    data class Success(val posts: List<Post>):FeedScreenUiState
}

@HiltViewModel
class FeedScreenViewModel  @Inject constructor(
    private val repository: FeedRepository,
):ViewModel() {

    private val _uiState = MutableStateFlow<FeedScreenUiState>(FeedScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getFeedPosts()
    }

    fun getFeedPosts(){
        viewModelScope.launch {
            _uiState.value = FeedScreenUiState.Loading
            val response = repository.getFeedPosts()
            when(response){
                is NetworkResult.Error -> {
                    _uiState.value = FeedScreenUiState.Error(message = response.message
                        ?: "An unexpected error occurred")
                }
                is NetworkResult.Success -> {
                    if (response.data.success){
                        _uiState.value = FeedScreenUiState.Success(posts = response.data.posts)
                    }else{
                        _uiState.value = FeedScreenUiState.Error(message =response.data.msg)
                    }

                }
                is NetworkResult.Exception -> {
                    _uiState.value = FeedScreenUiState.Error(message ="An unexpected error occurred")
                }
            }
        }
    }
}