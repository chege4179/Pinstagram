/*
 * Copyright 2023 PInstagram
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peterchege.pinstagram.feature.feature_feed.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.peterchege.pinstagram.core.core_model.external_models.User
import com.peterchege.pinstagram.core.core_ui.Post
import com.peterchege.pinstagram.core.core_ui.PostItem

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FeedScreen(
    bottomNavController: NavController,
    navHostController: NavHostController,
) {
    val defaultUser = User(
        bio = "djdjd",
        createdAt = "",
        fullName = "Peter  Chege",
        userId = "fff",
        username = "peterchege4179",
        followerIds = emptyList(),
        followingIds = emptyList(),
        email = "peterkagure@gmail.com",
        createdOn = "",
        password = "",
        profileImageUrl = "https://ui-avatars.com/api/?background=7462A7&color=fff&name=Peter+Chege&bold=true&fontsize=0.6&rounded=true"
    )
    val postList = listOf<Post>(
        Post(
            id = 1,
            image = "https://res.cloudinary.com/dhuqr5iyw/image/upload/v1651130156/blogger/c70b8d5a835c512ec5ba1c2992caff89.jpg",
            user = defaultUser,
            likesCount = 23,
            isLiked = false,
            timeStamp = 23,
            commentsCount = 23,
            caption = "Nice caption"

        ),
        Post(
            id = 2,
            image = "https://res.cloudinary.com/dhuqr5iyw/image/upload/v1651130156/blogger/c70b8d5a835c512ec5ba1c2992caff89.jpg",
            user = defaultUser,
            likesCount = 23,
            isLiked = false,
            timeStamp = 23,
            commentsCount = 23,
            caption = "Nice caption 2"

        ),
        Post(
            id = 3,
            image = "https://res.cloudinary.com/dhuqr5iyw/image/upload/v1651130156/blogger/c70b8d5a835c512ec5ba1c2992caff89.jpg",
            user = defaultUser,
            likesCount = 23,
            isLiked = false,
            timeStamp = 23,
            commentsCount = 23,
            caption = "Nice caption 3"

        ),
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            items(postList){
                PostItem(post = it)
            }
        }

    }

}