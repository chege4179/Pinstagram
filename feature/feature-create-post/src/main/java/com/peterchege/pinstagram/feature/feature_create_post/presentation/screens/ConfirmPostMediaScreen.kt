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
package com.peterchege.pinstagram.feature.feature_create_post.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.work.*
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.peterchege.pinstagram.core.core_common.UiEvent
import com.peterchege.pinstagram.core.core_model.external_models.MediaAsset
import com.peterchege.pinstagram.core.core_model.external_models.User
import com.peterchege.pinstagram.core.core_ui.PagerIndicator
import com.peterchege.pinstagram.core.core_ui.VideoPreview
import com.peterchege.pinstagram.feature.feature_create_post.presentation.CreatePostScreenViewModel
import com.peterchege.pinstagram.feature.feature_create_post.presentation.UploadPostFormState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ConfirmPostMediaScreen(

    viewModel: CreatePostScreenViewModel = hiltViewModel(),
){
    val user by viewModel.user.collectAsStateWithLifecycle()
    val isUploading by viewModel.isUploading.collectAsStateWithLifecycle()
    val formState by viewModel.formState.collectAsStateWithLifecycle()


    ConfirmPostMediaScreenContent(
        formState = formState,
        eventFlow = viewModel.eventFlow,
        onChangeCaption = { viewModel.setCaption(it) },
        onChangeMediaAssets = { viewModel.setMediaAssets(it) },
        onSubmit = { viewModel.startUpload(it) },
        user = user,
        isUploading = isUploading
    )


}


@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ConfirmPostMediaScreenContent(
    formState:UploadPostFormState,
    eventFlow: SharedFlow<UiEvent>,
    onChangeCaption:(String) -> Unit,
    onChangeMediaAssets:(List<MediaAsset>) -> Unit,
    onSubmit:(String) -> Unit,
    user: User?,
    isUploading:Boolean,


    ) {


    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current


    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText
                    )
                }
                is UiEvent.Navigate -> {

                }
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
                        count = formState.mediaAssets.size,
                        state = pagerState1
                    ) { image ->
                        val asset = formState.mediaAssets[image]
                        if (asset.isVideo()) {
                            VideoPreview(uriString = asset.uriString)
                        } else {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                SubcomposeAsyncImage(
                                    model = formState.mediaAssets[image].uriString,
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
                            value = formState.caption,
                            placeholder = {
                                Text(text = "Enter Caption")
                            },
                            onValueChange = {
                                onChangeCaption(it)
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
                                onChangeMediaAssets(emptyList())

                            }

                        }) {
                            Text(
                                text = "Cancel"
                            )

                        }

                        Button(
                            onClick = {
                                if (user != null) {
                                    onSubmit(user.userId)
                                }
                            }
                        ) {
                            Text(
                                text = "Post ${formState.mediaAssets.size}"
                            )

                        }

                    }
                }
            }
            if (isUploading){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

    }
}