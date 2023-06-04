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
package com.peterchege.pinstagram.feature.feature_create_post.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.pinstagram.core.core_common.UiEvent
import com.peterchege.pinstagram.core.core_datastore.repository.UserDataStoreRepository
import com.peterchege.pinstagram.core.core_model.external_models.MediaAsset
import com.peterchege.pinstagram.core.core_work.upload_post.UploadPostWorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UploadPostFormState(
    val mediaAssets:List<MediaAsset> = emptyList(),
    val caption:String = ""
)

@HiltViewModel
class CreatePostScreenViewModel @Inject constructor(

    private val userDataStoreRepository: UserDataStoreRepository,
    private val uploadPostWorkManager: UploadPostWorkManager,

    ) : ViewModel() {
    val user = userDataStoreRepository.getLoggedInUser()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = null
        )

    val isUploading = uploadPostWorkManager.isUploading
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = false
        )

    val _formState = MutableStateFlow(UploadPostFormState())
    val formState = _formState.asStateFlow()



    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun setCaption(text:String){
        _formState.value = _formState.value.copy(caption = text)
    }

    fun setMediaAssets(mediaAssetsState:List<MediaAsset>){
        _formState.value = _formState.value.copy(mediaAssets = mediaAssetsState)

    }

    fun clearMediaAssets(){
        _formState.value = _formState.value.copy(mediaAssets = emptyList())
    }

    fun startUpload(userId:String){
        val uris = _formState.value.mediaAssets.map { it.uriString }
        val combinedUris = uris.joinToString("||")
        viewModelScope.launch {
            uploadPostWorkManager.startUpload(
                userId = userId,
                caption = _formState.value.caption,
                uris = combinedUris
            )
        }

    }


}