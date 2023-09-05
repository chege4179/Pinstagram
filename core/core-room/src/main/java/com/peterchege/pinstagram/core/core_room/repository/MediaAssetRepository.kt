package com.peterchege.pinstagram.core.core_room.repository

import com.peterchege.pinstagram.core.core_room.entities.MediaAssetEntity
import kotlinx.coroutines.flow.Flow

interface MediaAssetRepository {

    suspend fun insertAllMediaAssets(mediaAssets:List<MediaAssetEntity>)

    fun getAllMediaAssets(): Flow<List<MediaAssetEntity>>

    suspend fun deleteAllMediaAssets()

    suspend fun deleteMediaAssetById(mediaAssetId:Long)


}