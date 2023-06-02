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
package com.peterchege.pinstagram.core.core_room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.peterchege.pinstagram.core.core_model.external_models.MediaAsset

@Entity(tableName = "mediaAssets")
data class MediaAssetEntity(
    @PrimaryKey
    val id: Long,
    val uriString: String,
    val filename: String,
    val directory: String,
    val size: Long,
    val mediaType: Int,
    val mimeType: String,
    val duration: Long?,
    val date: Long,
)

fun MediaAssetEntity.toExternalModel():MediaAsset{
    return MediaAsset(
        id = id,
        uriString = uriString,
        filename = filename,
        directory = directory,
        size = size,
        mediaType = mediaType,
        mimeType = mimeType,
        duration = duration,
        date = date
    )
}

fun MediaAsset.toEntity():MediaAssetEntity{
    return MediaAssetEntity(
        id = id,
        uriString = uriString,
        filename = filename,
        directory = directory,
        size = size,
        mediaType = mediaType,
        mimeType = mimeType,
        duration = duration,
        date = date
    )
}