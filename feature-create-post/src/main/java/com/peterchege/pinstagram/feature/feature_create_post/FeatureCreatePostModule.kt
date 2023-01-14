package com.peterchege.pinstagram.feature.feature_create_post

import android.app.Application
import com.peterchege.pinstagram.core.core_network.retrofit.RetrofitPinstagramNetwork
import com.peterchege.pinstagram.core.core_room.database.PinstagramLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object FeatureCreatePostModule {


    @Provides

    fun providePinstagramApi(): RetrofitPinstagramNetwork {
        return RetrofitPinstagramNetwork()
    }
    @Provides

    fun providePinstagramDatabase(app: Application): PinstagramLocalDataSource {
        return PinstagramLocalDataSource(app = app)
    }
}