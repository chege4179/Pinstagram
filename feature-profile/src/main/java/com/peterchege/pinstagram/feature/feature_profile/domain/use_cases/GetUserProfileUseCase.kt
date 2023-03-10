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
package com.peterchege.pinstagram.feature.feature_profile.domain.use_cases

import com.peterchege.pinstagram.core.core_common.Resource
import com.peterchege.pinstagram.core.core_model.response_models.GetUserByIdResponse
import com.peterchege.pinstagram.feature.feature_profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: ProfileRepository,

    ) {
    operator fun invoke(userId:String) : Flow<Resource<GetUserByIdResponse>> = flow {
        try {
            emit(Resource.Loading<GetUserByIdResponse>())
            val response = repository.getUserById( userId = userId)
            if (response.success) {
                emit(Resource.Success(response))
            }else{
                emit(Resource.Error<GetUserByIdResponse>( message = "Server error"))
            }

        }catch (e: HttpException){
            emit(
                Resource.Error<GetUserByIdResponse>(
                    message = e.localizedMessage ?: "Server error"))

        }catch (e: IOException){
            emit(
                Resource.Error<GetUserByIdResponse>(
                    message = "Could not reach server... Please check your internet connection"))

        }
    }
}