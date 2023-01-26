package com.peterchege.pinstagram.feature.feature_auth.di

import android.content.Context
import com.peterchege.pinstagram.core.core_datastore.repository.UserDataStoreRepository
import com.peterchege.pinstagram.core.core_network.retrofit.RetrofitPinstagramNetwork
import com.peterchege.pinstagram.feature.feature_auth.data.AuthRepositoryImpl
import com.peterchege.pinstagram.feature.feature_auth.domain.repository.AuthRepository
import com.peterchege.pinstagram.feature.feature_auth.domain.use_case.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestFeatureAuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(context : Context): AuthRepository {
        return AuthRepositoryImpl(
            api = RetrofitPinstagramNetwork(),
            userDataStoreRepository = UserDataStoreRepository(context),
        )

    }




}