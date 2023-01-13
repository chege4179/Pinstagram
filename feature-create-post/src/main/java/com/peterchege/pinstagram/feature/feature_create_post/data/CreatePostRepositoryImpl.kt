package com.peterchege.pinstagram.feature.feature_create_post.data

import android.content.Context
import com.peterchege.pinstagram.core.core_model.external_models.MediaAsset
import com.peterchege.pinstagram.core.core_model.external_models.User
import com.peterchege.pinstagram.core.core_model.response_models.UploadPostResponse
import com.peterchege.pinstagram.core.core_network.retrofit.RetrofitPinstagramNetwork
import com.peterchege.pinstagram.core.core_room.database.PinstagramLocalDataSource
import com.peterchege.pinstagram.core.core_room.entities.toEntity
import com.peterchege.pinstagram.core.core_room.entities.toExternalModel
import com.peterchege.pinstagram.feature.feature_create_post.domain.repository.CreatePostRepository
import javax.inject.Inject

class CreatePostRepositoryImpl @Inject constructor(
    private val db:PinstagramLocalDataSource,
    private val api:RetrofitPinstagramNetwork,
):CreatePostRepository{
    override suspend fun getAllMediaAssets(): List<MediaAsset> {
        return db.db.mediaAssetEntityDao.getMediaAssets().map { it.toExternalModel() }
    }
    override suspend fun deleteAllMediaAssets() {
        return db.db.mediaAssetEntityDao.deleteAllMediaAssets()
    }
    override suspend fun insertMediaAsset(mediaAsset: MediaAsset) {
        return db.db.mediaAssetEntityDao.insertMediaAsset(mediaAssetEntity = mediaAsset.toEntity())
    }

    override suspend fun uploadPost(assets: List<MediaAsset>, user: User,context: Context):UploadPostResponse {
        return api.uploadPost(assets = assets,user = user, context = context)
    }


}