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
package com.peterchege.pinstagram.core.core_network

import com.peterchege.pinstagram.core.core_model.external_models.User
import com.peterchege.pinstagram.core.core_model.request_models.LoginBody
import com.peterchege.pinstagram.core.core_model.request_models.SignUpBody
import com.peterchege.pinstagram.core.core_model.response_models.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface PinstgramAPI {
    @GET("/post/allPosts")
    suspend fun getFeedPosts(): Response<AllPostResponse>

    @POST("user/login")
    suspend fun loginUser(
        @Body loginBody :LoginBody
    ): Response<LoginResponse>

    @POST("user/signup")
    suspend fun signUpUser(
        @Body signUpBody:SignUpBody
    ):Response<SignUpResponse>

    @Multipart
    @POST("/post/create")
    suspend fun uploadPost(
        @Part assets:List<MultipartBody.Part>,
        @Part("userId") userId: String,
        @Part("caption") caption:String,
    ):Response<UploadPostResponse>

    @GET("/user/single/{userId}")
    suspend fun getUserById(
        @Path("userId") userId:String
    ):Response<GetUserByIdResponse>

    @GET("/user/search")
    suspend fun searchUsers(
        @Query("username") username:String
    ):Response<SearchUserResponse>
}