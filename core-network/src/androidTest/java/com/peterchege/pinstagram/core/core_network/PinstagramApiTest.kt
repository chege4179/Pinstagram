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
package com.peterchege.pinstagram.core.core_network

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.net.HttpURLConnection

class PinstagramApiTest {
    private var context: Context? = null
    private var mockWebServer = MockWebServer()
    private lateinit var pinstgramAPI: PinstgramAPI

    @OptIn(ExperimentalSerializationApi::class)
    @Before
    fun setup() {
        mockWebServer.start()

        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        context = InstrumentationRegistry.getInstrumentation().targetContext


        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val contentType = "application/json".toMediaType()
        val converterFactory = Json.asConverterFactory(contentType)

        pinstgramAPI = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(PinstgramAPI::class.java)

        val jsonStream: InputStream = context!!.assets.open("post.json")
        val jsonBytes: ByteArray = jsonStream.readBytes()


        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(String(jsonBytes))
        mockWebServer.enqueue(response)

    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun test_api_response(): Unit = runBlocking {
        val data = pinstgramAPI.getFeedPosts()
        Assert.assertEquals(data.success, true)

    }


}