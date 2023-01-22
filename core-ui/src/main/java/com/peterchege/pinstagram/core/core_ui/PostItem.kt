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
package com.peterchege.pinstagram.core.core_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.peterchege.pinstagram.core.core_model.external_models.User
import com.peterchege.pinstagram.core.core_model.response_models.Post
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostItem(
    post: Post,

){
    val pagerState1 = rememberPagerState(initialPage = 0)
    Column{
        Divider(thickness = 0.5.dp)
        PostTopBar(post = post)
        PostImage(post = post, pagerState1 = pagerState1)
        PostActionsRow(pagerState1 = pagerState1)
        PostLikesCount(post = post)
        PostCaption(post = post)
        Spacer(modifier = Modifier.height(2.dp))
        PostCommentsCount(post = post)
        Spacer(modifier = Modifier.height(4.dp))
        PostTimeStamp(post = post)
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun PostTopBar(
    modifier: Modifier = Modifier,
    post: Post
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = modifier.width(5.dp))
        RoundImage(
            modifier = modifier
                .size(40.dp)
                .weight(1f)
                ,
            image = rememberAsyncImagePainter(post.postCreator.profileImageUrl)
        )
        Text(
            text = post.postCreator.fullName,
            modifier = modifier
                .weight(8f)
                .padding(start = 10.dp),
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Outlined.MoreVert,
                contentDescription = "Settings",
                modifier = modifier
            )
        }


    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostActionsRow(
    modifier: Modifier = Modifier,
    pagerState1: PagerState
){
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = modifier
                    .fillMaxHeight()
                    .weight(1f)
                    ,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = "menu",
                        modifier = modifier
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.Chat,
                        contentDescription = "Comment",
                        modifier = modifier
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.Send,
                        contentDescription = "Share",
                        modifier = modifier
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    ) {
                        PagerIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            pagerState = pagerState1
                        ) {

                            coroutineScope.launch {
                                pagerState1.scrollToPage(it)
                            }
                        }
                    }

                }
                Row(
                    modifier = modifier
                        .fillMaxHeight()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.Bookmark,
                            contentDescription = "menu",
                            modifier = modifier
                        )
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostImage (
    modifier: Modifier = Modifier,
    post: Post,
    pagerState1:PagerState
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center,

    ) {


        HorizontalPager(
            count = post.postContent.size,
            state = pagerState1
        ) { image ->
            val asset = post.postContent[image]
            if (asset.postMediaType == "video"){
                VideoPreview(uriString = asset.postMediaURL)
            }else{
                Box(
                    modifier = Modifier.fillMaxWidth()
                ){
                    SubcomposeAsyncImage(
                        model =asset.postMediaURL,
                        loading = {
                            Box(modifier = Modifier.fillMaxSize()) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(
                                        Alignment.Center
                                    )
                                )
                            }
                        },
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentDescription = "Product Images"
                    )
                }
            }
        }
    }
}

@Composable
fun PostLikesCount(
    modifier: Modifier = Modifier,
    post: Post
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(30.dp)
            .padding(horizontal = 10.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = post.likes.size.toString().plus(" likes"),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 16.sp
        )
    }
}

@Composable
fun PostCaption(
    modifier: Modifier = Modifier,
    post: Post
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 10.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = buildAnnotatedString {
                val boldStyle = SpanStyle(
                    color = Color.Black,
                    fontWeight = Bold,
                    fontSize = 14.sp
                )
                val normalStyle = SpanStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
                pushStyle(boldStyle)
                append(post.postCreator.username)
                append(" ")
                if (post.postCaption.isNotEmpty()){
                    pushStyle(normalStyle)
                    append(post.postCaption)
                }
            }
        )
    }
}

 @Composable
 fun PostCommentsCount(
     modifier: Modifier = Modifier,
     post: Post
 ) {
     Row(
         modifier = modifier
             .fillMaxWidth()
             .wrapContentHeight()
             .padding(horizontal = 10.dp),
         verticalAlignment = Alignment.CenterVertically
     ) {
         Text(
             text = "View all ${post.comments.size} comments",
             color = Color.Black,
             fontWeight = FontWeight.Normal,
             fontSize = 14.sp
         )
     }

 }

 @Composable
 fun PostTimeStamp(
     modifier: Modifier = Modifier,
     post: Post
 ) {
     Row(
         modifier = modifier
             .fillMaxWidth()
             .wrapContentHeight()
             .padding(horizontal = 10.dp),
         verticalAlignment = Alignment.CenterVertically
     ) {
         Text(
             text = "${post.createdAt} ${post.createdOn} ",
             color = Color.Black,
             fontSize = 10.sp,
             fontWeight = FontWeight.Light
         )
     }

 }
@Composable
fun RoundImage(
    image: Painter,
    modifier: Modifier = Modifier
){
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = CircleShape
            )
            .padding(3.dp)
            .clip(CircleShape)
    )
}
