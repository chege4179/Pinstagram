package com.peterchege.compose_image_picker.constant

import com.peterchege.compose_image_picker.constant.RequestType

data class AssetPickerConfig(
    val maxAssets: Int = 9,
    val gridCount: Int = 3,
    val requestType: RequestType = RequestType.COMMON,
)