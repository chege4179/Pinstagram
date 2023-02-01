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
package com.peterchege.pinstagram.feature.feature_create_post.presentation.confirm_post

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.work.*
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.gson.Gson
import com.peterchege.pinstagram.core.core_common.UiEvent
import com.peterchege.pinstagram.core.core_network.util.UriToFile

import com.peterchege.pinstagram.core.core_ui.PagerIndicator
import com.peterchege.pinstagram.core.core_ui.VideoPreview
import com.peterchege.pinstagram.core.core_work.UploadPostWorker
import com.peterchege.pinstagram.feature.feature_create_post.presentation.confirm_post.ConfirmPostScreenViewModel
import com.peterchege.pinstagram.feature.feature_create_post.presentation.select_post.SelectPostScreensViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ConfirmPostMediaScreen(
    navController: NavController,
    viewModel: ConfirmPostScreenViewModel = hiltViewModel(),

    ) {
    val user = viewModel.user.collectAsState(initial = null)
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText
                    )
                }
                is UiEvent.Navigate -> {
                    navController.navigate(route = event.route)
                }
            }
        }
    }
    LaunchedEffect(key1 = true) {
        viewModel.getMediaAssets()
    }
    val workManager = WorkManager.getInstance(context)



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
                    if (asset.isVideo()) {
                        VideoPreview(uriString = asset.uriString)
                    } else {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            SubcomposeAsyncImage(
                                model = viewModel.mediaAssets.value[image].uriString,
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
                                contentDescription = "Post Assets"
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = viewModel.caption.value,
                        placeholder = {
                            Text(text = "Enter Caption")
                        },
                        onValueChange = {
                            viewModel.onChangeCaption(it)
                        })

                }

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
                        scope.launch {
                            viewModel.clearMediaAssets()
                        }
                        navController.popBackStack()
                    }) {
                        Text(
                            text = "Cancel"
                        )

                    }

                    Button(
                        onClick = {
                            if(user.value != null){
                                val requestFiles = viewModel.mediaAssets.value.map {
                                    UriToFile(context = context).prepareImagePart(Uri.parse(it.uriString), it.filename)
                                }
//                                viewModel.uploadPost(
//                                    requestFiles = requestFiles,
//                                    scaffoldState = scaffoldState,
//                                    user = user.value!!
//                                )
                                val requestFilesString = Gson().toJson(requestFiles)
                                val postArticleParams = workDataOf(
                                    "caption" to viewModel.caption.value,
                                    "userId" to user.value!!.userId,
                                    "assets" to requestFilesString,

                                )
                                val uploadPostRequest = OneTimeWorkRequestBuilder<UploadPostWorker>()
                                    .setInputData(postArticleParams)
                                    .setConstraints(
                                        Constraints.Builder()
                                            .setRequiredNetworkType(
                                                NetworkType.CONNECTED
                                            )
                                            .build()
                                    )
                                    .build()
                                workManager
                                    .beginUniqueWork(
                                         "uploadPost",
                                        ExistingWorkPolicy.KEEP,
                                        uploadPostRequest
                                    ).enqueue()
                            }
                        }
                    ) {
                        Text(
                            text = "Post ${viewModel.mediaAssets.value.size}"
                        )

                    }

                }

            }
        }
    }
}