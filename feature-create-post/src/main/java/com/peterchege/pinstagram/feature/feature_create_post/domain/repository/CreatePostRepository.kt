package com.peterchege.pinstagram.feature.feature_create_post.domain.repository

import android.content.Context
import com.peterchege.pinstagram.core.core_model.external_models.MediaAsset
import com.peterchege.pinstagram.core.core_model.external_models.User
import com.peterchege.pinstagram.core.core_model.response_models.UploadPostResponse

interface CreatePostRepository {

    suspend fun getAllMediaAssets():List<MediaAsset>

    suspend fun deleteAllMediaAssets()

    suspend fun insertMediaAsset(mediaAsset: MediaAsset)

    suspend fun uploadPost(assets:List<MediaAsset>,user:User,context: Context):UploadPostResponse


}