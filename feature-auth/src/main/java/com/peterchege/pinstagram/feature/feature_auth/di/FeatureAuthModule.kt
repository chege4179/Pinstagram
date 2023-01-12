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


