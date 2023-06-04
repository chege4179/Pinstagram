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
package com.peterchege.pinstagram.feature.feature_profile.data


import android.util.Log
import com.peterchege.pinstagram.core.core_common.Resource
import com.peterchege.pinstagram.core.core_datastore.repository.UserDataStoreRepository
import com.peterchege.pinstagram.core.core_model.response_models.GetUserByIdResponse
import com.peterchege.pinstagram.core.core_network.repository.NetworkDataSource
import com.peterchege.pinstagram.core.core_network.util.NetworkResult
import com.peterchege.pinstagram.feature.feature_profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.*

import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: NetworkDataSource,
    private val userDataStoreRepository: UserDataStoreRepository,

    ) : ProfileRepository {
    override suspend fun getUserById(userId: String): NetworkResult<GetUserByIdResponse> {
        return api.getUserById(userId = userId)

    }
    val TAG = "PROFILE_REPOSITORY"

    override fun getLoggedInUserById(): Flow<Resource<GetUserByIdResponse>> =
        channelFlow {
            send(Resource.Loading())
            try {
                val userFlow = userDataStoreRepository.getLoggedInUser()
                userFlow.collectLatest { user ->
                    Log.e(TAG,"Fetched user ${user.toString()}")
                    send(
                        Resource.Success(
                            data = GetUserByIdResponse(
                                msg = "User fetched from datastore",
                                success = true,
                                user = user!!,
                                posts = emptyList(),
                            )
                        )
                    )
                    val response = api.getUserById(user.userId)
                    when(response){
                        is NetworkResult.Error -> {
                            send(Resource.Error(message = response.message ?:"An exception occurred"))
                        }
                        is NetworkResult.Success -> {
                            send(Resource.Success(data = response.data))
                        }
                        is NetworkResult.Exception -> {
                            send(Resource.Error(message = response.e.message ?:"An exception occurred"))
                        }
                    }
                }

            } catch (e: HttpException) {
                send(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                send(Resource.Error(message = e.localizedMessage ?: "An unexpected error occurred"))
            }
        }

}