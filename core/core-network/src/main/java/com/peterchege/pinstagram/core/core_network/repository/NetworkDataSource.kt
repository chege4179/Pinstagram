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
import com.peterchege.pinstagram.core.core_model.response_models.*
import com.peterchege.pinstagram.core.core_network.util.NetworkResult
import okhttp3.MultipartBody

interface NetworkDataSource {

    suspend fun loginUser(loginBody: LoginBody):NetworkResult<LoginResponse>

    suspend fun getFeedPosts(): NetworkResult<AllPostResponse>

    suspend fun signUpUser(signUpBody: SignUpBody):NetworkResult<SignUpResponse>

    suspend fun uploadPost(
        assets:List<MultipartBody.Part>,
        userId:String,
        caption:String,
    ):NetworkResult<UploadPostResponse>

    suspend fun getUserById(userId:String) :NetworkResult<GetUserByIdResponse>


    suspend fun searchUsers(username:String):NetworkResult<SearchUserResponse>



}