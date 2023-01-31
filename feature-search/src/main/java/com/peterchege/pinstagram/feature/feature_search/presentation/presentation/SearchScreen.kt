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
package com.peterchege.pinstagram.feature.feature_search.presentation.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.peterchege.pinstagram.core.core_common.UiEvent
import com.peterchege.pinstagram.core.core_ui.ProfileCard
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    bottomNavController: NavController,
    navHostController: NavHostController,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.uiText)
                }
                is UiEvent.Navigate -> {
                    navHostController.navigate(route = event.route)
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.username.value,
                onValueChange = {
                    viewModel.onChangeUsername(it)
                },
                placeholder = {
                    Text(text = "Search Username")
                }
            )
            if (viewModel.isLoading.value){
                Box(
                    modifier = Modifier.fillMaxSize()
                ){
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }else{
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ){
                    items(items = viewModel.users.value){ user ->
                        ProfileCard(
                            user = user,
                            onProfileNavigate = {

                            },
                        )
                    }
                }
            }
        }


    }

}