package com.peterchege.pinstagram.feature.feature_feed.di

import com.peterchege.pinstagram.core.core_network.retrofit.RetrofitPinstagramNetwork
import com.peterchege.pinstagram.feature.feature_feed.data.FeedRepositoryImpl
import com.peterchege.pinstagram.feature.feature_feed.domain.repository.FeedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeatureFeedModule {


    @Provides
    @Singleton
    fun provideFeedRepository():FeedRepository{
        return FeedRepositoryImpl(
            api = RetrofitPinstagramNetwork()
        )

    }



}