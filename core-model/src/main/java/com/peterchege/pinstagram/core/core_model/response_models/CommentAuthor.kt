package com.peterchege.pinstagram.core.core_model.response_models

data class CommentAuthor(
    val bio: Any,
    val createdAt: String,
    val createdOn: String,
    val email: String,
    val fullName: String,
    val password: String,
    val profileImageUrl: String,
    val userId: String,
    val username: String
)