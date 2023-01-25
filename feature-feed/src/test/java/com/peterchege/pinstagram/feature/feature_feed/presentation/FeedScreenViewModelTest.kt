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