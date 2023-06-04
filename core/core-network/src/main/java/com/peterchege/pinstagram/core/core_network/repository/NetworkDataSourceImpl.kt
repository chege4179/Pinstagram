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
package com.peterchege.pinstagram.core.core_network.repository

import com.peterchege.pinstagram.core.core_model.request_models.LoginBody
import com.peterchege.pinstagram.core.core_model.request_models.SignUpBody
import com.peterchege.pinstagram.core.core_model.response_models.AllPostResponse
import com.peterchege.pinstagram.core.core_model.response_models.GetUserByIdResponse
import com.peterchege.pinstagram.core.core_model.response_models.LoginResponse
import com.peterchege.pinstagram.core.core_model.response_models.SearchUserResponse
import com.peterchege.pinstagram.core.core_model.response_models.SignUpResponse
import com.peterchege.pinstagram.core.core_model.response_models.UploadPostResponse
import com.peterchege.pinstagram.core.core_network.PinstgramAPI
import com.peterchege.pinstagram.core.core_network.util.NetworkResult
import com.peterchege.pinstagram.core.core_network.util.handleApi
import okhttp3.MultipartBody
import javax.inject.Inject


class NetworkDataSourceImpl @Inject constructor(
    private val networkApi: PinstgramAPI,

    ) : NetworkDataSource {


    override suspend fun loginUser(loginBody: LoginBody): NetworkResult<LoginResponse> {
        return handleApi { networkApi.loginUser(loginBody = loginBody) }
    }

    override suspend fun getFeedPosts(): NetworkResult<AllPostResponse> {
        return handleApi { networkApi.getFeedPosts() }
    }

    override suspend fun signUpUser(signUpBody: SignUpBody): NetworkResult<SignUpResponse> {
        return  handleApi { networkApi.signUpUser(signUpBody = signUpBody) }
    }

    override suspend fun uploadPost(
        assets: List<MultipartBody.Part>,
        userId: String,
        caption: String
    ): NetworkResult<UploadPostResponse> {
        return handleApi { networkApi.uploadPost(assets = assets, userId = userId, caption = caption) }
    }

    override suspend fun getUserById(userId: String): NetworkResult<GetUserByIdResponse> {
        return handleApi { networkApi.getUserById(userId = userId) }
    }

    override suspend fun searchUsers(username: String): NetworkResult<SearchUserResponse> {
        return handleApi { networkApi.searchUsers(username = username) }
    }
}