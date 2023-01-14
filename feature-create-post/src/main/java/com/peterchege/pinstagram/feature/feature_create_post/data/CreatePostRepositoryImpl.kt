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
package com.peterchege.pinstagram.feature.feature_create_post.data

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.peterchege.pinstagram.core.core_common.Constants
import com.peterchege.pinstagram.core.core_model.external_models.MediaAsset
import com.peterchege.pinstagram.core.core_model.external_models.User
import com.peterchege.pinstagram.core.core_model.response_models.UploadPostResponse
import com.peterchege.pinstagram.core.core_network.PinstgramAPI
import com.peterchege.pinstagram.core.core_network.retrofit.RetrofitPinstagramNetwork
import com.peterchege.pinstagram.core.core_room.database.PinstagramDatabase
import com.peterchege.pinstagram.core.core_room.database.PinstagramLocalDataSource
import com.peterchege.pinstagram.core.core_room.entities.toEntity
import com.peterchege.pinstagram.core.core_room.entities.toExternalModel
import com.peterchege.pinstagram.feature.feature_create_post.domain.repository.CreatePostRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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