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
package com.peterchege.pinstagram.feature.feature_feed.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.peterchege.pinstagram.core.core_common.TestTags
import com.peterchege.pinstagram.core.core_common.UiEvent
import com.peterchege.pinstagram.core.core_ui.PostItem
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun FeedScreen(
    viewModel: FeedScreenViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FeedScreenContent(

        uiState = uiState,
        eventFlow = viewModel.eventFlow,
        retryCallBack = { viewModel.getFeedPosts() }
    )

}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FeedScreenContent(
    uiState: FeedScreenUiState,
    eventFlow:SharedFlow<UiEvent>,
    retryCallBack:() -> Unit,

) {
    val scaffoldState = rememberScaffoldState()
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
        topBar ={
            TopAppBar(
                title = {
                    Text(text = "Pinstagram")
                }
            )
        }
    ) {
        when(uiState){
            is FeedScreenUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()){
                    CircularProgressIndicator(modifier = Modifier
                        .align(Alignment.Center)
                        .testTag(TestTags.FEED_LOADING_CIRCULAR_INDICATOR)
                    )
                }
            }
            is FeedScreenUiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(text = uiState.message)
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { retryCallBack() }
                    ){
                        Text(text = "Retry")
                    }
                }
            }
            is FeedScreenUiState.Success -> {
                val posts = uiState.posts
                if (posts.isEmpty()){
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(text = "No posts were found")
                    }
                }else{
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ){
                        items(items =  posts){
                            PostItem(post = it)
                        }
                    }
                }
            }
        }
    }
}