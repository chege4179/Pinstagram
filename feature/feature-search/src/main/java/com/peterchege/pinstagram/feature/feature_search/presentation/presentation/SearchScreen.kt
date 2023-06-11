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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.peterchege.pinstagram.core.core_common.UiEvent
import com.peterchege.pinstagram.core.core_model.response_models.toUser
import com.peterchege.pinstagram.core.core_ui.ProfileCard
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    navigateToUserProfile: (String) -> Unit,
) {
    val searchFormState by viewModel.searchFormState.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SearchScreenContent(

        eventFlow = viewModel.eventFlow,
        uiState = uiState,
        searchFormState = searchFormState,
        onSearchText = { viewModel.onChangeUsername(it) },
        navigateToUserProfile = navigateToUserProfile
    )


}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreenContent(
    navigateToUserProfile: (String) -> Unit,
    eventFlow: SharedFlow<UiEvent>,
    uiState: SearchScreenUiState,
    searchFormState: String,
    onSearchText: (String) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.uiText)
                }

                is UiEvent.Navigate -> {

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
                value = searchFormState,
                onValueChange = {
                    onSearchText(it)
                },
                placeholder = {
                    Text(text = "Search Username")
                }
            )
            when (uiState) {
                is SearchScreenUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

                is SearchScreenUiState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(text = uiState.message)
                    }

                }

                is SearchScreenUiState.Idle -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(text = "Start searching ")
                    }
                }

                is SearchScreenUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(items = uiState.searchResults) { user ->
                            ProfileCard(
                                user = user.toUser(),
                                onProfileNavigate = {
                                    navigateToUserProfile(it)


                                },
                            )
                        }
                    }
                }
            }

        }


    }

}