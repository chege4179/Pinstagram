package com.peterchege.pinstagram.feature.feature_auth

import com.peterchege.pinstagram.feature.feature_auth.domain.use_case.LoginUseCase
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var fakeAuthRepository: FakeAuthRepository

    @Before
    fun setUp(){
        fakeAuthRepository = FakeAuthRepository()
        loginUseCase = LoginUseCase(repository = fakeAuthRepository)


    }

    @Test
    fun loginUser(){

    }




}