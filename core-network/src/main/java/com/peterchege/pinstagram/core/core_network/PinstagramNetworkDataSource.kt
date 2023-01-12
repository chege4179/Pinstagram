package com.peterchege.pinstagram.core.core_network

import com.peterchege.pinstagram.core.core_model.request_models.LoginBody
import com.peterchege.pinstagram.core.core_model.request_models.SignUpBody
import com.peterchege.pinstagram.core.core_model.response_models.LoginResponse
import com.peterchege.pinstagram.core.core_model.response_models.SignUpResponse

interface  PinstagramNetworkDataSource {

    suspend fun loginUser(loginBody: LoginBody):LoginResponse

    suspend fun signUpUser(signUpBody: SignUpBody):SignUpResponse



}