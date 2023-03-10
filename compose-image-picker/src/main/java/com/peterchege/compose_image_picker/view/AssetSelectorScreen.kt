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
package com.peterchege.compose_image_picker.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.VideoFrameDecoder
import coil.request.ImageRequest
import com.peterchege.compose_image_picker.data.AssetDirectory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AssetSelectorScreen(
    directory: String,
    assetDirectories: List<AssetDirectory>,
    navigateUp: () -> Unit,
    onClick: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            DirectoryTopAppBar(directory = directory, navigateUp = navigateUp)
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            DirectorySelector(
                directory = directory,
                assetDirectories = assetDirectories,
                onClick = onClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DirectoryTopAppBar(
    directory: String,
    navigateUp: () -> Unit,
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.statusBarsPadding(),
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(Icons.Filled.Close, contentDescription = "")
            }
        },
        title = {
            Row(modifier = Modifier.clickable(onClick = navigateUp)) {
                Text(directory, fontSize = 18.sp)
                Icon(Icons.Default.KeyboardArrowUp, contentDescription = "")
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DirectorySelector(
    directory: String,
    assetDirectories: List<AssetDirectory>,
    onClick: (String) -> Unit,
) {
    LazyColumn {
        items(assetDirectories) {
            val itemDirectory = it.directory
            ListItem(
                modifier = Modifier.clickable { onClick(itemDirectory) },
                icon = {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(it.cover ?: Icons.Default.Place)
                            .decoderFactory(VideoFrameDecoder.Factory())
                            .build(),
                        modifier = Modifier
                            .size(32.dp)
                            .aspectRatio(1.0F),
                        filterQuality = FilterQuality.Low,
                        contentScale = ContentScale.Crop,
                        contentDescription = ""
                    )
                },
                text = {
                    Row {
                        Text(text = itemDirectory, color = MaterialTheme.colorScheme.onSurface)
                        Text(text = "(${it.counts})", color = Color.Gray)
                    }
                },
                trailing = { TrailingIcon(directory, itemDirectory) }
            )
        }
    }
}