package com.peterchege.pinstagram.feature.feature_auth.di

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.datastore.dataStore
import com.peterchege.pinstagram.core.core_datastore.UserInfoSerializer
import com.peterchege.pinstagram.core.core_model.external_models.User
import com.peterchege.pinstagram.core.core_network.PinstagramNetworkDataSource
import com.peterchege.pinstagram.core.core_network.retrofit.RetrofitPinstagramNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@Module
@InstallIn(SingletonComponent::class)
object FeatureAuthModule {


    @Provides
    fun providePinstagramApi(): RetrofitPinstagramNetwork {
        return RetrofitPinstagramNetwork()
    }



}


