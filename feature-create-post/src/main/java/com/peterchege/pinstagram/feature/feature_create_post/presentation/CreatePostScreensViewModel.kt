package com.peterchege.pinstagram.feature.feature_create_post.presentation

import android.content.Context
import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.pinstagram.core.core_datastore.repository.UserDataStoreRepository
import com.peterchege.pinstagram.core.core_model.external_models.MediaAsset
import com.peterchege.pinstagram.feature.feature_create_post.data.CreatePostRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostScreensViewModel @Inject constructor(
    private val createPostRepository: CreatePostRepositoryImpl,
    private val userDataStoreRepository: UserDataStoreRepository,

) : ViewModel() {

    val _mediaAssets = mutableStateOf<List<MediaAsset>>(emptyList())
    val mediaAssets : State<List<MediaAsset>> = _mediaAssets

    val _caption = mutableStateOf("")
    val caption: State<String> = _caption

    fun onChangeCaption(text:String){
        _caption.value = text
    }

    suspend fun setMediaAssets(mediaAssetsState:List<MediaAsset>){
        Log.e("Create Post Screen","Set Media Assets ")
        mediaAssetsState.forEach {
            createPostRepository.insertMediaAsset(mediaAsset = it)
        }


    }
    suspend fun clearMediaAssets(){
        createPostRepository.deleteAllMediaAssets()

    }
    suspend fun getMediaAssets(){
        Log.e("Create Post Screen","Get Media Assets ")
        _mediaAssets.value = createPostRepository.getAllMediaAssets()


    }

    fun uploadPost(context: Context, scaffoldState: ScaffoldState){
        viewModelScope.launch {
            val user = userDataStoreRepository.getLoggedInUser()
            user.collect{
                val response = createPostRepository.uploadPost(
                    assets = mediaAssets.value,
                    user = it!!,
                    context = context,
                    caption = _caption.value
                )
                scaffoldState.snackbarHostState.showSnackbar(
                    message = response.msg
                )
                createPostRepository.deleteAllMediaAssets()
            }

        }


    }


}