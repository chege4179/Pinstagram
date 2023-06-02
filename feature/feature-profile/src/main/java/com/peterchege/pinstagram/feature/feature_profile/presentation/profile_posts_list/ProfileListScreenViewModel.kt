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
import com.peterchege.pinstagram.core.core_model.response_models.Post
import com.peterchege.pinstagram.feature.feature_profile.domain.use_cases.GetUserProfileUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileListScreenViewModel @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository,
    private val getUserProfileUseCase: GetUserProfileUseCase,

    private val savedStateHandle: SavedStateHandle
):ViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _msg = mutableStateOf("")
    val msg: State<String> = _msg

    private val _postId = mutableStateOf("")
    val postId: State<String> = _postId

    val _posts = mutableStateOf<List<Post>>(emptyList())
    val posts : State<List<Post>> = _posts



    init {
        savedStateHandle.get<String>("postId")?.let {
            viewModelScope.launch {
                getLoggedInUserProfile(getUserProfileUseCase = getUserProfileUseCase)
                _postId.value = it
            }


        }

    }




    private suspend fun getLoggedInUserProfile(getUserProfileUseCase: GetUserProfileUseCase){
        val loggedInUser = userDataStoreRepository.getLoggedInUser()
        loggedInUser.collect{
            getUserProfileUseCase(it!!.userId).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.e("success","success")
                        _isLoading.value = false
                        _msg.value = result.data!!.msg
                        _posts.value = result.data!!.posts


                    }
                    is Resource.Error -> {
                        Log.e("error","error")
                        _isLoading.value = false
                        _msg.value = result.data!!.msg
                    }
                    is Resource.Loading -> {
                        Log.e("loading","loading")
                        _isLoading.value = true

                    }
                }
            }.launchIn(viewModelScope)
        }

    }
}