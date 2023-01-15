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
package com.peterchege.pinstagram.feature.feature_create_post.presentation.confirm_post_media_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

import com.peterchege.pinstagram.core.core_common.Screens
import com.peterchege.pinstagram.core.core_ui.PagerIndicator
import com.peterchege.pinstagram.core.core_ui.VideoPreview
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ConfirmPostMediaScreen(
    navController: NavController,
    viewModel: ConfirmPostMediaScreenViewModel = hiltViewModel(),

    ) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Confirm Post") }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                val pagerState1 = rememberPagerState(initialPage = 0)
                val coroutineScope = rememberCoroutineScope()
                HorizontalPager(
                    count = viewModel.mediaAssets.value.size,
                    state = pagerState1
                ) { image ->
                    val asset = viewModel.mediaAssets.value[image]
                    if (asset.isVideo()){
                        VideoPreview(uriString = asset.uriString)
                    }else{
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ){
                            SubcomposeAsyncImage(
                                model =viewModel.mediaAssets.value[image].uriString,
                                loading = {
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.align(
                                                Alignment.Center
                                            )
                                        )
                                    }
                                },
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp),
                                contentDescription = "Product Images"
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                ) {
                    PagerIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        pagerState = pagerState1
                    ) {
                        coroutineScope.launch {
                            pagerState1.scrollToPage(it)
                        }
                    }
                }

            }
            item {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.caption.value,
                    onValueChange = {
                        viewModel.onChangeCaption(it)
                    })
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,

                ) {
                    Button(onClick = {
                        viewModel.cancelPost()
                        navController.navigate(Screens.SELECT_POST_MEDIA_SCREEN)
                    }) {
                        Text(
                            text= "Cancel"
                        )

                    }

                    Button(onClick = {
                        viewModel.uploadPost(context = context,scaffoldState = scaffoldState)
                    }) {
                        Text(
                            text= "Post"
                        )

                    }

                }

            }
        }
    }
}