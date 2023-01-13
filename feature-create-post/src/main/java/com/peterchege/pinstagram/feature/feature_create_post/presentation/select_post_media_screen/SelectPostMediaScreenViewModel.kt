package com.peterchege.pinstagram.feature.feature_create_post.presentation.select_post_media_screen

import androidx.lifecycle.ViewModel
import com.peterchege.pinstagram.core.core_model.external_models.MediaAsset
import com.peterchege.pinstagram.feature.feature_create_post.data.CreatePostRepositoryImpl

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SelectPostMediaScreenViewModel @Inject constructor(
    private val createPostRepository: CreatePostRepositoryImpl
) :ViewModel() {


    suspend fun loadSelectedMediaToDatabase(mediaAssets:List<MediaAsset>){
        mediaAssets.map {
            createPostRepository.insertMediaAsset(it)
        }
    }

}