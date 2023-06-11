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
package com.peterchege.pinstagram.feature.feature_search.presentation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.pinstagram.core.core_common.Resource
import com.peterchege.pinstagram.core.core_common.UiEvent
import com.peterchege.pinstagram.core.core_model.response_models.PostCreator
import com.peterchege.pinstagram.feature.feature_search.presentation.domain.use_case.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SearchScreenUiState {
    object Idle : SearchScreenUiState
    object Loading : SearchScreenUiState
    data class Error(val message: String) :SearchScreenUiState
    data class Success(val searchResults: List<PostCreator>):SearchScreenUiState
}


@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) :ViewModel(){

    private val _uiState = MutableStateFlow<SearchScreenUiState>(SearchScreenUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _searchFormState = MutableStateFlow("")
    val searchFormState = _searchFormState.asStateFlow()

    private var searchJob: Job? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onChangeUsername(text:String){
        _searchFormState.value = text
        searchJob?.cancel()
        if (text.length > 3){
            searchJob = viewModelScope.launch {
                delay(500L)
                searchUseCase(username = text)
                    .onEach { result ->
                        when(result) {
                            is Resource.Success -> {
                                _uiState.value = SearchScreenUiState.Success(
                                    searchResults = result.data?.users ?: emptyList()
                                )
                            }
                            is Resource.Error -> {
                                _uiState.value = SearchScreenUiState.Error(
                                    message = result.message ?: "Unknown error"
                                )

                            }
                            is Resource.Loading -> {
                                _uiState.value = SearchScreenUiState.Loading
                            }
                        }
                    }.launchIn(this)
            }
        }
        if(text.isEmpty()){
            _uiState.value = SearchScreenUiState.Idle
        }

    }

}