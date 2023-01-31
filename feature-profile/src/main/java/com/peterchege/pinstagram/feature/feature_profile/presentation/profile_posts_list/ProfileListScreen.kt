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
package com.peterchege.pinstagram.feature.feature_profile.presentation.profile_posts_list

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.peterchege.pinstagram.core.core_ui.PostItem


@Composable
fun ProfileListScreen(
    navController: NavController,
    viewModel: ProfileListScreenViewModel = hiltViewModel()
) {
    val scrollState = rememberLazyListState()


    Box(modifier = Modifier.fillMaxSize()){
        if (viewModel.isLoading.value){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }else{
            LazyColumn(
                state = scrollState,
                modifier = Modifier.fillMaxSize()
            ){
                items(viewModel.posts.value){
                    PostItem(post = it)
                }
            }
        }
    }
    
}