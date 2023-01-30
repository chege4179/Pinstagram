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
package com.peterchege.pinstagram.feature.feature_feed.domain.use_cases

import com.peterchege.pinstagram.core.core_common.Resource
import com.peterchege.pinstagram.core.core_model.external_models.User
import com.peterchege.pinstagram.core.core_model.response_models.Post
import com.peterchege.pinstagram.feature.feature_feed.domain.repository.FakeFeedRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetFeedUseCaseTest {
    private lateinit var getFeedUseCase: GetFeedUseCase
    private lateinit var fakeFeedRepository: FakeFeedRepository
    @Before
    fun setUp(){
        fakeFeedRepository = FakeFeedRepository()
        getFeedUseCase = GetFeedUseCase(repository =fakeFeedRepository)

    }

    @Test
    fun `get Feed Posts make sure the state is loading first`() = runBlocking {

        val result = getFeedUseCase.invoke().first()

        assert((result is Resource.Loading))
    }

    @Test
    fun `get Posts and make sure the Posts are not empty`() = runBlocking {
        val result =  getFeedUseCase.invoke().last()
        val data  = result.data


        if (data != null) {
            assert((result is Resource.Success) && data.posts.isNotEmpty())
        }

    }






}