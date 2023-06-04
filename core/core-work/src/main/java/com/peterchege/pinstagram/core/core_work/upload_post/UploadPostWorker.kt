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
package com.peterchege.pinstagram.core.core_work.upload_post

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.peterchege.pinstagram.core.core_common.Constants
import com.peterchege.pinstagram.core.core_network.repository.NetworkDataSource
import com.peterchege.pinstagram.core.core_network.util.NetworkResult
import com.peterchege.pinstagram.core.core_network.util.UriToFile
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlin.random.Random



@HiltWorker
class UploadPostWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val api: NetworkDataSource,

    ) : CoroutineWorker(context, workerParams) {


    override suspend fun doWork(): Result {
        val userId = inputData.getString("userId")
        val caption = inputData.getString("caption")
        val uris = inputData.getString("uris")?.split("||")
        val requestFiles = uris?.map {
            UriToFile(context = context).prepareImagePart(Uri.parse(it), getRandomString(10))
        }

        startForegroundService()


        val response = api.uploadPost(
            assets = requestFiles!!,
            userId = userId!!,
            caption = caption!!,
        )
        when(response){
            is NetworkResult.Success -> {
                successForegroundService(msg = response.data.msg)
                return Result.success()
            }
            is NetworkResult.Error -> {
                failureForegroundService("Could not reach server at the moment")
                Result.retry()

                return Result.failure()
            }
            is NetworkResult.Exception -> {
                failureForegroundService(msg = "The server is down ....Please try again later")
                Result.retry()
                return Result.failure()
            }
        }

    }

    private suspend fun startForegroundService() {
        setForeground(
            ForegroundInfo(
                Random.nextInt(),
                NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL)
                    .setSmallIcon(androidx.core.R.drawable.notification_action_background)
                    .setContentText("Uploading your post...")
                    .setContentTitle("Your post is being uploaded")
                    .build()
            )
        )

    }

    private suspend fun successForegroundService(msg: String) {
        setForeground(
            ForegroundInfo(
                Random.nextInt(),
                NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL)
                    .setSmallIcon(androidx.core.R.drawable.notification_action_background)
                    .setContentText(msg)
                    .setContentTitle(msg)
                    .build()
            )
        )

    }

    private suspend fun failureForegroundService(msg: String) {
        setForeground(
            ForegroundInfo(
                Random.nextInt(),
                NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL)
                    .setSmallIcon(androidx.core.R.drawable.notification_action_background)
                    .setContentText(msg)
                    .setContentTitle(msg)
                    .build()
            )
        )

    }

    private fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

}