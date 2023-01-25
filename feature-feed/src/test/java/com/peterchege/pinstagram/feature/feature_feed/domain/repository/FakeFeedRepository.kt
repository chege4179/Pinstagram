package com.peterchege.pinstagram.feature.feature_feed.domain.repository

import com.peterchege.pinstagram.core.core_model.external_models.User
import com.peterchege.pinstagram.core.core_model.response_models.AllPostResponse
import com.peterchege.pinstagram.core.core_model.response_models.Post

class FakeFeedRepository: FeedRepository {

    val fakePosts = ('a'..'z').map {  c ->
        Post(
            createdAt = c.toString(),
            createdOn = c.toString(),
            postCaption = c.toString(),
            postContent = emptyList(),
            postAuthorId =c.toString(),
            likes = emptyList<User>(),
            comments = emptyList(),
            views = emptyList(),
            postId = c.toString(),
            postCreator = User(
                bio = c.toString(),
                createdAt = c.toString(),
                createdOn = c.toString(),
                email = c.toString(),
                following = emptyList(),
                followers = emptyList(),
                fullName = c.toString(),
                userId = c.toString(),
                username = c.toString(),
                profileImageUrl = c.toString(),
                password = c.toString(),
            )
        )
    }


    override suspend fun getFeedPosts(): AllPostResponse {
        return AllPostResponse(
            msg="Posts Fetched successfully",
            success = true,
            posts = fakePosts,
        )
    }
}