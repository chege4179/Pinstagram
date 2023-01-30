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
package com.peterchege.pinstagram.feature.feature_auth.domain.use_case

import android.content.Context
import android.util.Log
import com.peterchege.pinstagram.core.core_common.Resource
import com.peterchege.pinstagram.core.core_model.request_models.LoginBody
import com.peterchege.pinstagram.core.core_model.response_models.LoginResponse
import com.peterchege.pinstagram.feature.feature_auth.data.AuthRepositoryImpl
import com.peterchege.pinstagram.feature.feature_auth.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject




class LoginUseCase @Inject constructor(
    private val repository: AuthRepository,

    ) {
    operator fun invoke(loginUser: LoginBody) : Flow<Resource<LoginResponse>> = flow {
        try {

            emit(Resource.Loading<LoginResponse>())
            val loginResponse = repository.loginUser(loginUser)
            if (loginResponse.success) {
                repository.setLoggedInUser(user = loginResponse.user!!)
                emit(Resource.Success(loginResponse))
            }else{
                emit(Resource.Error<LoginResponse>( message = "Wrong Password"))
            }

        }catch (e: HttpException){
            emit(Resource.Error<LoginResponse>(
                message = e.localizedMessage ?: "Log In failed......Server error"))

        }catch (e: IOException){
            emit(Resource.Error<LoginResponse>(
                message = "Could not reach server... Please check your internet connection"))

        }
    }
}

