package com.peterchege.pinstagram.core.core_ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.outlined.Home
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
import coil.compose.rememberImagePainter
import com.peterchege.pinstagram.core.core_model.external_models.User


data class Post(
    val id : Int,
    val image: String,
    val user: User,
    val likesCount: Int,
    val isLiked: Boolean = false,
    val caption: String,
    val commentsCount: Int,
    val timeStamp: Int
)
@Composable
fun PostItem(
    post: Post
){
    Column{
        Divider(thickness = 0.5.dp)
        PostTopBar(post = post)
        PostImage(post = post)
        PostActionsRow()
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
            image = rememberImagePainter(post.user.profileImageUrl)
        )
        Text(
            text = post.user.fullName,
            modifier = modifier
                .weight(8f)
                .padding(start = 10.dp),
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Outlined.Home,
                contentDescription = "menu",
                modifier = modifier
            )
        }


    }

}

@Composable
fun PostActionsRow(
    modifier: Modifier = Modifier,
){
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
                        imageVector = Icons.Outlined.Home,
                        contentDescription = "menu",
                        modifier = modifier
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = "menu",
                        modifier = modifier
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = "menu",
                        modifier = modifier
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {

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
                            imageVector = Icons.Outlined.Home,
                            contentDescription = "menu",
                            modifier = modifier
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun PostImage (
    modifier: Modifier = Modifier,
    post: Post
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center,

    ) {
        SubcomposeAsyncImage(
            model = post.image,
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
            contentDescription = "Post Image"
        )
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
            text = post.likesCount.toString().plus(" likes"),
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
                append(post.user.username)
                append(" ")
                if (post.caption.isNotEmpty()){
                    pushStyle(normalStyle)
                    append(post.caption)
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
             text = "View all ${post.commentsCount} comments",
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
             text = "${post.timeStamp} hours ago ",
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
