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

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.pinstagram.core.core_common.Resource
import com.peterchege.pinstagram.core.core_common.UiEvent
import com.peterchege.pinstagram.core.core_model.external_models.User
import com.peterchege.pinstagram.feature.feature_search.presentation.domain.use_case.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) :ViewModel(){

    val _username = mutableStateOf<String>("")
    val username: State<String> = _username

    val _isLoading = mutableStateOf<Boolean>(false)
    val isLoading: State<Boolean> = _isLoading

    private var searchJob: Job? = null

    val _users = mutableStateOf<List<User>>(emptyList())
    val users :State<List<User>> = _users

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onChangeUsername(text:String){
        _username.value = text
        searchJob?.cancel()
        if (text.length > 3){
            searchJob = viewModelScope.launch {
                delay(500L)
                searchUseCase(username = text)
                    .onEach { result ->
                        when(result) {
                            is Resource.Success -> {
                                _isLoading.value = false
                                _users.value = result.data?.users ?: emptyList()
                                if (_users.value.isEmpty()){
                                    _eventFlow.emit(UiEvent.ShowSnackbar(
                                        uiText =  "No users found"
                                    ))
                                }
                            }
                            is Resource.Error -> {
                                _isLoading.value = false
                                _eventFlow.emit(UiEvent.ShowSnackbar(
                                    uiText = result.message ?: "Unknown error"
                                ))
                            }
                            is Resource.Loading -> {
                                _isLoading.value = true

                            }
                        }
                    }.launchIn(this)
            }
        }

    }

}