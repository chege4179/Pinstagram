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
    fun getMediaAssets(): Flow<List<MediaAssetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMediaAsset(mediaAssetEntity: MediaAssetEntity)


    @Query("DELETE FROM mediaAssets WHERE id = :id")
    suspend fun deleteMediaAssetById(id: Long)

    @Query("DELETE FROM mediaAssets")
    suspend fun deleteAllMediaAssets()

}