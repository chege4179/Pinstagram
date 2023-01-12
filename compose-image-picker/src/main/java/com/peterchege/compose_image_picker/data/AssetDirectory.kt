package com.peterchege.compose_image_picker.data

data class AssetDirectory(
    val directory: String,
    val assets: List<AssetInfo>,
    val cover: String? = assets.firstOrNull()?.uriString,
    val counts: Int = assets.size
)