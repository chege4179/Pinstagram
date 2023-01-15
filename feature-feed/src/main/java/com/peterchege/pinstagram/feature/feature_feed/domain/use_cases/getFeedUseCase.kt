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

import android.util.Log
import com.peterchege.pinstagram.core.core_common.Resource
import com.peterchege.pinstagram.core.core_model.response_models.AllPostResponse
import com.peterchege.pinstagram.feature.feature_feed.data.FeedRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFeedUseCase @Inject constructor(
    private val repository: FeedRepositoryImpl,

) {
    operator fun invoke() : Flow<Resource<AllPostResponse>> = flow {
        try {
            Log.e("use case","here")
            emit(Resource.Loading<AllPostResponse>())
            val response = repository.getFeedPosts()
            if (response.success) {
                emit(Resource.Success(response))
            }else{
                emit(Resource.Error<AllPostResponse>( message = "Server error"))
            }

        }catch (e: HttpException){
            emit(
                Resource.Error<AllPostResponse>(
                message = e.localizedMessage ?: "Server error"))

        }catch (e: IOException){
            emit(
                Resource.Error<AllPostResponse>(
                message = "Could not reach server... Please check your internet connection"))

        }
    }
}