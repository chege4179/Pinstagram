package com.peterchege.pinstagram.core.core_network

import com.peterchege.pinstagram.core.core_model.request_models.LoginBody
import com.peterchege.pinstagram.core.core_model.request_models.SignUpBody
import com.peterchege.pinstagram.core.core_model.response_models.LoginResponse
import com.peterchege.pinstagram.core.core_model.response_models.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PinstgramAPI {



    @POST("/user/login")
    suspend fun loginUser(
        @Body loginBody :LoginBody
    ): LoginResponse

    @POST("/user/signup")
    suspend fun signUpUser(
        @Body signUpBody:SignUpBody
    ):SignUpResponse
}