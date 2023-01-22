package com.peterchege.pinstagram.core.core_model.response_models

data class Comment(
    val commentAuthor: CommentAuthor,
    val commentAuthorId: String,
    val commentContent: String,
    val commentCreatedAt: String,
    val commentCreatedOn: String,
    val commentId: String,
    val commentPostId: String
)