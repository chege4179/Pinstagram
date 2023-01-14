package com.peterchege.pinstagram.core.core_network

import com.peterchege.pinstagram.core.core_network.retrofit.RetrofitPinstagramNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun providePinstagramApi(): RetrofitPinstagramNetwork {
        return RetrofitPinstagramNetwork()
    }

}