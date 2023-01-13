package com.peterchege.pinstagram.feature.feature_create_post.domain

import com.peterchege.compose_image_picker.data.AssetInfo
import kotlinx.serialization.json.Json

fun convertAssetToString(asset:AssetInfo):String{
    return Json.encodeToString(AssetInfo.serializer(),asset)
}

fun convertStringToAsset(assetString:String):AssetInfo{
    return Json.decodeFromString(AssetInfo.serializer(),assetString)
}