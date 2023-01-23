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
package com.peterchege.pinstagram.feature.feature_auth.data


import com.peterchege.pinstagram.core.core_common.Constants
import com.peterchege.pinstagram.core.core_datastore.repository.UserDataStoreRepository
import com.peterchege.pinstagram.core.core_model.external_models.User
import com.peterchege.pinstagram.core.core_model.request_models.LoginBody
import com.peterchege.pinstagram.core.core_model.request_models.SignUpBody
import com.peterchege.pinstagram.core.core_model.response_models.LoginResponse
import com.peterchege.pinstagram.core.core_model.response_models.SignUpResponse
import com.peterchege.pinstagram.core.core_network.PinstgramAPI
import com.peterchege.pinstagram.core.core_network.retrofit.RetrofitPinstagramNetwork
import com.peterchege.pinstagram.feature.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor (
    private val api:RetrofitPinstagramNetwork,
    private val userDataStoreRepository: UserDataStoreRepository,

) :AuthRepository {
    override suspend fun loginUser(loginBody: LoginBody): LoginResponse {
        return  api.loginUser(loginBody = loginBody)

    }
    override suspend fun signUpUser(signUpBody: SignUpBody): SignUpResponse {
        return api.signUpUser(signUpBody = signUpBody)
    }
    fun getLoggedInUser(): Flow<User?> {
        return userDataStoreRepository.getLoggedInUser()
    }
    suspend fun setLoggedInUser(user:User) {
        return userDataStoreRepository.setLoggedInUser(user = user)
    }
    suspend fun unsetLoggedInUser() {
        return userDataStoreRepository.unsetLoggedInUser()
    }
}