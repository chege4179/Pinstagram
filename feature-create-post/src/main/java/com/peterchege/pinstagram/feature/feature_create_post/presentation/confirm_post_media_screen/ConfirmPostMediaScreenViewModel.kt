package com.peterchege.pinstagram.feature.feature_create_post.presentation.confirm_post_media_screen


import android.content.Context
import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.peterchege.pinstagram.core.core_datastore.repository.UserDataStoreRepository
import com.peterchege.pinstagram.core.core_model.external_models.MediaAsset
import com.peterchege.pinstagram.feature.feature_create_post.data.CreatePostRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject


@HiltViewModel
class ConfirmPostMediaScreenViewModel @Inject constructor(
    private val createPostRepository: CreatePostRepositoryImpl,
    private val userDataStoreRepository: UserDataStoreRepository,

) : ViewModel() {
    val _mediaAssets = mutableStateOf<List<MediaAsset>>(emptyList())
    val mediaAssets : State<List<MediaAsset>> = _mediaAssets

    init {
        viewModelScope.launch {
            val assets = getSelectedMediaFromDatabase()
            _mediaAssets.value = assets

        }
    }
    private suspend fun getSelectedMediaFromDatabase(): List<MediaAsset> {
        return createPostRepository.getAllMediaAssets()
    }

    fun cancelPost(){
        viewModelScope.launch {
            createPostRepository.deleteAllMediaAssets()
        }
    }

    fun uploadPost(context: Context,scaffoldState: ScaffoldState){
        viewModelScope.launch {
            val user = userDataStoreRepository.getLoggedInUser()
            user.collect{
                val response = createPostRepository.uploadPost(assets = mediaAssets.value,user = it!!,context = context)

                scaffoldState.snackbarHostState.showSnackbar(
                    message = response.msg
                )

            }

        }


    }














}