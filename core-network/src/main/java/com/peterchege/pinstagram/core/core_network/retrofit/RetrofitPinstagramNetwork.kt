package com.peterchege.pinstagram.core.core_network.retrofit

import android.provider.SyncStateContract
import com.peterchege.pinstagram.core.core_common.Constants
import com.peterchege.pinstagram.core.core_common.Constants.BASE_URL
import com.peterchege.pinstagram.core.core_model.request_models.LoginBody
import com.peterchege.pinstagram.core.core_model.request_models.SignUpBody
import com.peterchege.pinstagram.core.core_model.response_models.LoginResponse
import com.peterchege.pinstagram.core.core_model.response_models.SignUpResponse
import com.peterchege.pinstagram.core.core_network.PinstagramNetworkDataSource
import com.peterchege.pinstagram.core.core_network.PinstgramAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitPinstagramNetwork : PinstagramNetworkDataSource {

    private val networkApi = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .build()
    .create(PinstgramAPI::class.java)

    override suspend fun loginUser(loginBody: LoginBody): LoginResponse {
        return networkApi.loginUser(loginBody = loginBody)
    }

    override suspend fun signUpUser(signUpBody: SignUpBody): SignUpResponse {
        return networkApi.signUpUser(signUpBody = signUpBody)
    }
}