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