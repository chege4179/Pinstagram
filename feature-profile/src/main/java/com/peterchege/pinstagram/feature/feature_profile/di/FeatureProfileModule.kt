package com.peterchege.pinstagram.feature.feature_profile.di

import com.peterchege.pinstagram.core.core_network.retrofit.RetrofitPinstagramNetwork
import com.peterchege.pinstagram.feature.feature_profile.data.ProfileRepositoryImpl
import com.peterchege.pinstagram.feature.feature_profile.domain.repository.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FeatureProfileModule {

    @Provides
    @Singleton
    fun provideProfileRepository(): ProfileRepository {
        return ProfileRepositoryImpl(
            api = RetrofitPinstagramNetwork()
        )

    }

}