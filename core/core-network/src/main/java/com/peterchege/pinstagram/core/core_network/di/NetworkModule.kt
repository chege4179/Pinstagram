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
package com.peterchege.pinstagram.core.core_network.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.peterchege.pinstagram.core.core_common.Constants.BASE_URL
import com.peterchege.pinstagram.core.core_network.PinstgramAPI
import com.peterchege.pinstagram.core.core_network.repository.NetworkDataSource
import com.peterchege.pinstagram.core.core_network.repository.NetworkDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideNetworkJson():Json = Json {
        ignoreUnknownKeys = true
    }

    @Singleton
    @Provides
    fun provideOkhttpClient(
        @ApplicationContext context:Context,
    ):OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                ChuckerInterceptor.Builder(context = context)
                    .collector(ChuckerCollector(context = context))
                    .maxContentLength(length = 250000L)
                    .redactHeaders(headerNames = emptySet())
                    .alwaysReadResponseBody(enable = false)
                    .build()
            )
            .connectTimeout(1200, TimeUnit.SECONDS)
            .readTimeout(1200, TimeUnit.SECONDS)
            .writeTimeout(900, TimeUnit.SECONDS)
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofitClient(
        @ApplicationContext context:Context,
        networkJson:Json,
        client:OkHttpClient,
    ): PinstgramAPI {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(PinstgramAPI::class.java)
    }


    @Singleton
    @Provides
    fun provideNetworkRepository(
        api:PinstgramAPI
    ): NetworkDataSource {
        return NetworkDataSourceImpl(api)
    }


}