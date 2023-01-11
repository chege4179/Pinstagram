package com.peterchege.pinstagram.core.core_model

import kotlinx.serialization.Serializable


@Serializable
data class User(
    val bio: String?,
    val createdAt: String,
    val createdOn: String,
    val email: String,
    val followerIds: List<String>,
    val followingIds: List<String>,
    val fullName: String,
    val password: String,
    val profileImageUrl: String,
    val userId: String,
    val username: String
)