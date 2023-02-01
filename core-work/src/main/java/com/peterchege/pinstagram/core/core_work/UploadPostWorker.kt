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
package com.peterchege.pinstagram.core.core_work

import android.content.Context
import android.net.Uri
import android.provider.SyncStateContract
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.google.gson.Gson
import com.peterchege.pinstagram.core.core_common.Constants
import com.peterchege.pinstagram.core.core_model.external_models.User
import com.peterchege.pinstagram.core.core_network.retrofit.RetrofitPinstagramNetwork
import com.peterchege.pinstagram.core.core_network.util.UriToFile
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.random.Random



@Suppress("BlockingMethodInNonBlockingContext")
class UploadPostWorker (
     private val context: Context,
     workerParams: WorkerParameters,
) :  CoroutineWorker(context,workerParams) {
    private val api = RetrofitPinstagramNetwork()

    override suspend fun doWork(): Result {
        val userId = inputData.getString("userId")
        val caption = inputData.getString("caption")
        val assets = inputData.getString("assets")

        val requestFiles = Gson().fromJson(assets, Array<MultipartBody.Part>::class.java).toList()
        startForegroundService()

        try {
            val response = api.uploadPost(
                assets = requestFiles,
                userId = userId!!,
                caption = caption!!,
            )
            if (response.success){
                successForegroundService(msg = response.msg)
                Result.success(
                    workDataOf(
                    WorkerKeys.MSG to response.msg,
                    WorkerKeys.IS_LOADING to false,
                    WorkerKeys.SUCCESS to response.success
                    )
                )
            }

        } catch (e: HttpException) {
            failureForegroundService("Could not reach server at the moment")

            Result.failure(
                workDataOf(
                WorkerKeys.MSG to "Could not reach server at the moment",
                WorkerKeys.IS_LOADING to false,
                WorkerKeys.SUCCESS to false
                )
            )

        } catch (e: IOException) {

            failureForegroundService(msg = "The server is down ....Please try again later")
            Result.failure(
                workDataOf(
                    WorkerKeys.MSG to "The server is down ....Please try again later",
                    WorkerKeys.IS_LOADING to false,
                    WorkerKeys.SUCCESS to false
                )
            )

        }
        return Result.failure(
            workDataOf(WorkerKeys.MSG to "Unknown error")
        )


    }

    private suspend fun startForegroundService(){
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
    private suspend fun successForegroundService(msg:String){
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

    private suspend fun failureForegroundService(msg:String){
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

}