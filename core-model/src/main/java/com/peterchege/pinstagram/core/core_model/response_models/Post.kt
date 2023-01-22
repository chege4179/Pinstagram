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
package com.peterchege.pinstagram.core.core_model.response_models

import com.peterchege.pinstagram.core.core_model.external_models.User


data class Post(
    val createdAt: String,
    val createdOn: String,
    val postCaption: String,
    val postId: String,
    val likes: List<User>,
    val postCreator: User,
    val postAuthorId: String,
    val views: List<User>,
    val postContent: List<PostMedia>,
    val comments:List<Comment>,
)