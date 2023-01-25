package com.peterchege.pinstagram.feature.feature_auth

import com.peterchege.pinstagram.core.core_model.external_models.User
import com.peterchege.pinstagram.core.core_model.request_models.LoginBody
import com.peterchege.pinstagram.core.core_model.request_models.SignUpBody
import com.peterchege.pinstagram.core.core_model.response_models.LoginResponse
import com.peterchege.pinstagram.core.core_model.response_models.SignUpResponse

import com.peterchege.pinstagram.feature.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class FakeAuthRepository :AuthRepository {
    override suspend fun loginUser(loginBody: LoginBody): LoginResponse {
        if(loginBody.email =="peterkagure@gmail.com" && loginBody.password== "test"){
            return LoginResponse(
                msg ="Login Successful",
                success = true,
                user = User(
                    bio = "",
                    createdAt = "",
                    createdOn = "",
                    email = "",
                    following = emptyList(),
                    followers = emptyList(),
                    fullName = "",
                    userId = "",
                    username = "",
                    profileImageUrl = "",
                    password = "",

                ),
                jwtToken = ""
            )
        }else{
            return LoginResponse(
                msg ="Wrong Credentials",
                success = false,
                user = null,
                jwtToken = ""
            )
        }
    }

    override suspend fun signUpUser(signUpBody: SignUpBody): SignUpResponse {
        TODO("Not yet implemented")
    }

    override fun getLoggedInUser(): Flow<User?> {
        TODO("Not yet implemented")
    }

    override suspend fun setLoggedInUser(user: User) {
        TODO("Not yet implemented")
    }

}