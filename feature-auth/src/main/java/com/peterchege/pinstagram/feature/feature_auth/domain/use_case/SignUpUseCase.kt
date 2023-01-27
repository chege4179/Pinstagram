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
import com.peterchege.pinstagram.core.core_model.request_models.SignUpBody
import com.peterchege.pinstagram.core.core_model.response_models.LoginResponse
import com.peterchege.pinstagram.core.core_model.response_models.SignUpResponse
import com.peterchege.pinstagram.feature.feature_auth.data.AuthRepositoryImpl
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject




class SignUpUseCase @Inject constructor(
    private val repository: AuthRepositoryImpl,

    ) {
    operator fun invoke(signUpBody :SignUpBody): Flow<Resource<SignUpResponse>> = flow {
        try {
            Log.e("use case","here")
            emit(Resource.Loading<SignUpResponse>())
            val loginResponse = repository.signUpUser(signUpBody = signUpBody)
            if (loginResponse.success) {

                emit(Resource.Success(loginResponse))
            }else{
                emit(Resource.Error<SignUpResponse>( message = "Wrong Password"))
            }

        }catch (e: HttpException){
            emit(Resource.Error<SignUpResponse>(
                message = e.localizedMessage ?: "Sign Up failed......Server error"))

        }catch (e: IOException){
            emit(Resource.Error<SignUpResponse>(
                message = "Could not reach server... Please check your internet connection"))

        }
    }
}

