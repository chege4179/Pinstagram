package com.peterchege.pinstagram.core.core_model.external_models

import kotlinx.serialization.Serializable


@Serializable
data class Follower (
    val followerId:String,
    val followerUserId:String,
    val followedUserId:String,


        )