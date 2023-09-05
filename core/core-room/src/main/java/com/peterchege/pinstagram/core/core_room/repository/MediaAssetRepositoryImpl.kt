package com.peterchege.pinstagram.core.core_room.repository

import com.peterchege.pinstagram.core.core_common.IoDispatcher
import com.peterchege.pinstagram.core.core_room.database.PinstagramDatabase
import com.peterchege.pinstagram.core.core_room.entities.MediaAssetEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MediaAssetRepositoryImpl @Inject constructor(
    private val db:PinstagramDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
):MediaAssetRepository {
    override suspend fun insertAllMediaAssets(mediaAssets: List<MediaAssetEntity>) {
        withContext(ioDispatcher){
            mediaAssets.map {
                db.mediaAssetEntityDao.insertMediaAsset(it)
            }
        }
    }
    override fun getAllMediaAssets(): Flow<List<MediaAssetEntity>> {
        return db.mediaAssetEntityDao.getMediaAssets().flowOn(ioDispatcher)
    }

    override suspend fun deleteAllMediaAssets() {
        withContext(ioDispatcher){
            db.mediaAssetEntityDao.deleteAllMediaAssets()
        }
    }

    override suspend fun deleteMediaAssetById(mediaAssetId: Long) {
        withContext(ioDispatcher){
            db.mediaAssetEntityDao.deleteMediaAssetById(mediaAssetId)
        }
    }

}