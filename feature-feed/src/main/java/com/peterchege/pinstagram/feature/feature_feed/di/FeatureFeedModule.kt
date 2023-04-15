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
package com.peterchege.pinstagram.feature.feature_feed.di

import android.content.Context
import com.peterchege.pinstagram.core.core_network.retrofit.RetrofitPinstagramNetwork
import com.peterchege.pinstagram.feature.feature_feed.data.FeedRepositoryImpl
import com.peterchege.pinstagram.feature.feature_feed.domain.repository.FeedRepository
import com.peterchege.pinstagram.feature.feature_feed.domain.use_cases.GetFeedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeatureFeedModule {


    @Provides
    @Singleton
    fun provideFeedRepository(@ApplicationContext context: Context):FeedRepository{
        return FeedRepositoryImpl(
            api = RetrofitPinstagramNetwork(context)
        )
    }

    @Provides
    @Singleton
    fun provideGetFeedUseCase(feedRepository: FeedRepository):GetFeedUseCase{
        return GetFeedUseCase(repository = feedRepository)
    }

}