package com.peterchege.pinstagram.core.core_model.response_models

import com.peterchege.pinstagram.core.core_model.external_models.User

data class GetUserByIdResponse(
    val msg: String,
    val posts: List<Post>,
    val success: Boolean,
    val user: User
)