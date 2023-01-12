package com.peterchege.pinstagram.core.core_model.response_models

import com.peterchege.pinstagram.core.core_model.external_models.User

data class LoginResponse (
    val msg:String,
    val success:Boolean,
    val user: User?,
    val jwtToken:String,
    )