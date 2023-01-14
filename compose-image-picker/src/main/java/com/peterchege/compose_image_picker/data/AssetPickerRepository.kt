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
package com.peterchege.compose_image_picker.data

import android.content.Context
import android.net.Uri
import com.peterchege.compose_image_picker.constant.RequestType

internal class AssetPickerRepository(
    private val context: Context
) {
    suspend fun getAssets(requestType: RequestType): List<AssetInfo> {
        return AssetLoader.load(context, requestType)
    }

    fun insertImage(): Uri? {
        return AssetLoader.insertImage(context)
    }

    fun findByUri(uri: Uri?): AssetInfo? {
        return uri?.let {
            AssetLoader.findByUri(context, it)
        }
    }

    fun deleteByUri(uri: Uri?) {
        uri?.let {
            AssetLoader.deleteByUri(context, it)
        }
    }
}