package com.peterchege.pinstagram.feature.feature_auth.data

import com.peterchege.pinstagram.core.core_model.request_models.LoginBody
import com.peterchege.pinstagram.core.core_model.request_models.SignUpBody
import com.peterchege.pinstagram.core.core_model.response_models.LoginResponse
import com.peterchege.pinstagram.core.core_model.response_models.SignUpResponse
import com.peterchege.pinstagram.core.core_network.retrofit.RetrofitPinstagramNetwork
import com.peterchege.pinstagram.feature.feature_auth.domain.repository.AuthRepository
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor (
    private val api:RetrofitPinstagramNetwork
) :AuthRepository {
    override suspend fun loginUser(loginBody: LoginBody): LoginResponse {
        return  api.loginUser(loginBody = loginBody)

    }
    override suspend fun signUpUser(signUpBody: SignUpBody): SignUpResponse {
        return api.signUpUser(signUpBody = signUpBody)
    }
}