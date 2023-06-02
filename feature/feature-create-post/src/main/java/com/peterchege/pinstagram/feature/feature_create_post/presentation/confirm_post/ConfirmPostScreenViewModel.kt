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
package com.peterchege.pinstagram.feature.feature_create_post.presentation.confirm_post

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
import com.peterchege.pinstagram.feature.feature_create_post.domain.repository.CreatePostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject


@HiltViewModel
class ConfirmPostScreenViewModel @Inject constructor(
    private val userDataStoreRepository: UserDataStoreRepository,
    private val createPostRepository: CreatePostRepository,

) :ViewModel() {
    val user = userDataStoreRepository.getLoggedInUser()

    val mediaAssetsEntities = createPostRepository.getAllMediaAssets()


    val _caption = mutableStateOf("")
    val caption: State<String> = _caption

    val _combinedUrisState = mutableStateOf("")
    val combinedUrisState: State<String> = _combinedUrisState

    fun onChangeCombinedUris(combinedUris:String){
        _combinedUrisState.value = combinedUris

    }

    suspend fun showSnackBar(text:String){
        _eventFlow.emit(UiEvent.ShowSnackbar(uiText = text))
    }

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onChangeCaption(text:String){
        _caption.value = text
    }

    suspend fun clearMediaAssets(){
        createPostRepository.deleteAllMediaAssets()

    }

}