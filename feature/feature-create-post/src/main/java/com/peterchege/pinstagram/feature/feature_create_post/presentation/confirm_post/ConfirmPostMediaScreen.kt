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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import com.peterchege.pinstagram.core.core_common.UiEvent
import com.peterchege.pinstagram.core.core_room.entities.toExternalModel

import com.peterchege.pinstagram.core.core_ui.PagerIndicator
import com.peterchege.pinstagram.core.core_ui.VideoPreview
import com.peterchege.pinstagram.core.core_work.upload_post.UploadPostWorker
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

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val mediaAssets = viewModel.mediaAssetsEntities
        .collectAsState(initial = emptyList())
        .value
        .map { it.toExternalModel() }

    val postArticleParams = workDataOf(
        "caption" to viewModel.caption.value,
        "userId" to (user.value?.userId ?: ""),
        "uris" to viewModel.combinedUrisState.value,
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

    val workManager = WorkManager.getInstance(context)
    val uploadWorkInfo = workManager
        .getWorkInfosForUniqueWorkLiveData("uploadPost")
        .observeAsState()
        .value
    val workInfo = remember(key1 = uploadWorkInfo) {
        uploadWorkInfo?.find { it.id == uploadPostRequest.id }
    }

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

    LaunchedEffect(key1 = true, ){
        when(workInfo?.state) {
            WorkInfo.State.RUNNING -> {

            }
            WorkInfo.State.SUCCEEDED -> {

                viewModel.showSnackBar("Upload finished")
            }
            WorkInfo.State.FAILED -> {
                viewModel.showSnackBar("Upload failed")
            }
            WorkInfo.State.CANCELLED -> {
                viewModel.showSnackBar("Upload started")
            }
            WorkInfo.State.ENQUEUED -> {
                viewModel.showSnackBar("Upload enqueued")
            }
            WorkInfo.State.BLOCKED -> {
                viewModel.showSnackBar("Upload blocked")
            }
            else -> {

            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Confirm Post") }
            )
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    val pagerState1 = rememberPagerState(initialPage = 0)
                    val coroutineScope = rememberCoroutineScope()
                    HorizontalPager(
                        count = mediaAssets.size,
                        state = pagerState1
                    ) { image ->
                        val asset = mediaAssets[image]
                        if (asset.isVideo()) {
                            VideoPreview(uriString = asset.uriString)
                        } else {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                SubcomposeAsyncImage(
                                    model = mediaAssets[image].uriString,
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
                                if (user.value != null) {
                                    val uris = mediaAssets.map { it.uriString }
                                    val combinedUris = uris.joinToString("||")
                                    viewModel.onChangeCombinedUris(combinedUris = combinedUris)

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
                                text = "Post ${mediaAssets.size}"
                            )

                        }

                    }
                }
            }
            when(workInfo?.state) {
                WorkInfo.State.RUNNING -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                else -> {

                }
            }
        }

    }
}