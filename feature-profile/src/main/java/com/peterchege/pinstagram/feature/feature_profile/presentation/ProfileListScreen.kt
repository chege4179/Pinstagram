package com.peterchege.pinstagram.feature.feature_profile.presentation

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
    viewModel:ProfileListScreenViewModel = hiltViewModel()
) {
    val scrollState = rememberLazyListState()
    LaunchedEffect(key1 = viewModel.posts.value.isNotEmpty()){
        val postIds = viewModel.posts.value.map { it.postId }
        Log.e("Post Ids",postIds.toString())
        Log.e("Passed Post id",viewModel.postId.value)
        scrollState.animateScrollToItem(postIds.indexOf(viewModel.postId.value))

    }

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