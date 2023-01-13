package com.peterchege.pinstagram.core.core_room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peterchege.pinstagram.core.core_room.entities.MediaAssetEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MediaAssetEntityDao {

    @Query("SELECT * FROM mediaAssets")
    suspend fun getMediaAssets(): List<MediaAssetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaAsset(mediaAssetEntity: MediaAssetEntity)


    @Query("DELETE FROM mediaAssets WHERE id = :id")
    suspend fun deleteMediaAssetById(id: Long)

    @Query("DELETE FROM mediaAssets")
    suspend fun deleteAllMediaAssets()

}