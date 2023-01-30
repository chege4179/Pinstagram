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
package com.peterchege.pinstagram.feature.feature_create_post.presentation.select_post

import android.content.Context
import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.pinstagram.core.core_common.UiEvent
import com.peterchege.pinstagram.core.core_datastore.repository.UserDataStoreRepository
import com.peterchege.pinstagram.core.core_model.external_models.MediaAsset
import com.peterchege.pinstagram.core.core_model.external_models.User
import com.peterchege.pinstagram.feature.feature_create_post.data.CreatePostRepositoryImpl
import com.peterchege.pinstagram.feature.feature_create_post.domain.repository.CreatePostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectPostScreensViewModel @Inject constructor(
    private val createPostRepository: CreatePostRepository,
    private val userDataStoreRepository: UserDataStoreRepository,

    ) : ViewModel() {
    val user = userDataStoreRepository.getLoggedInUser()

    val _mediaAssets = mutableStateOf<List<MediaAsset>>(emptyList())
    val mediaAssets : State<List<MediaAsset>> = _mediaAssets


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun setMediaAssets(mediaAssetsState:List<MediaAsset>){
        Log.e("Create Post Screen","Set Media Assets ${mediaAssetsState.size}")
        viewModelScope.launch {
            mediaAssetsState.map {
                createPostRepository.insertMediaAsset(mediaAsset = it)
            }
        }
    }
    suspend fun clearMediaAssets(){
        createPostRepository.deleteAllMediaAssets()
    }


}