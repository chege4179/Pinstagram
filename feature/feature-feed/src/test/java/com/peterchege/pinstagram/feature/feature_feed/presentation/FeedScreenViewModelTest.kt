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

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.peterchege.pinstagram.feature.feature_feed.domain.use_cases.GetFeedUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class FeedScreenViewModelTest {
    private lateinit var  feedScreenViewModel: FeedScreenViewModel
    private val getFeedUseCase:GetFeedUseCase = mock()


    @get: Rule
    val mainDispatcherRule = MainDispatcherRule()


    @Before
    fun setUp() {
        feedScreenViewModel = FeedScreenViewModel(getFeedUseCase = getFeedUseCase)
        verify(getFeedUseCase).invoke()
    }



    @Test
    fun `Screen fetchs feed posts`() = runTest {
        feedScreenViewModel.getFeedPosts(getFeedUseCase = getFeedUseCase)

        verify(getFeedUseCase, times(1))
    }
}