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
package com.peterchege.pinstagram.core.core_network.retrofit

import android.content.Context
import android.net.Uri

import com.peterchege.pinstagram.core.core_common.Constants
import com.peterchege.pinstagram.core.core_common.Constants.BASE_URL
import com.peterchege.pinstagram.core.core_model.external_models.MediaAsset
import com.peterchege.pinstagram.core.core_model.external_models.User
import com.peterchege.pinstagram.core.core_model.request_models.LoginBody
import com.peterchege.pinstagram.core.core_model.request_models.SignUpBody
import com.peterchege.pinstagram.core.core_model.response_models.*
import com.peterchege.pinstagram.core.core_network.PinstagramNetworkDataSource
import com.peterchege.pinstagram.core.core_network.PinstgramAPI
import com.peterchege.pinstagram.core.core_network.util.UriToFile
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitPinstagramNetwork : PinstagramNetworkDataSource {

    private fun makeOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1200, TimeUnit.SECONDS)
            .readTimeout(1200, TimeUnit.SECONDS)
            .writeTimeout(900, TimeUnit.SECONDS)
            .build()
    }

    private val networkApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .client(makeOkHttpClient())
        .build()
        .create(PinstgramAPI::class.java)

    override suspend fun loginUser(loginBody: LoginBody): LoginResponse {
        return networkApi.loginUser(loginBody = loginBody)
    }

    override suspend fun getFeedPosts(): AllPostResponse {
        return networkApi.getFeedPosts()
    }

    override suspend fun signUpUser(signUpBody: SignUpBody): SignUpResponse {
        return networkApi.signUpUser(signUpBody = signUpBody)
    }



    override suspend fun uploadPost(
        assets: List<MultipartBody.Part>,
        userId: String,
        caption: String
    ): UploadPostResponse {

        return networkApi.uploadPost(assets = assets, userId = userId, caption = caption)
    }

    override suspend fun getUserById(userId: String): GetUserByIdResponse {
        return networkApi.getUserById(userId = userId)
    }

    override suspend fun searchUsers(username: String): SearchUserResponse {
        return networkApi.searchUsers(username = username)
    }
}