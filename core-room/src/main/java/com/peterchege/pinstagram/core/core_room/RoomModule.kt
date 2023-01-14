package com.peterchege.pinstagram.core.core_room

import android.app.Application
import com.peterchege.pinstagram.core.core_room.database.PinstagramLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {


    @Provides
    fun providePinstagramDatabase(app: Application): PinstagramLocalDataSource {
        return PinstagramLocalDataSource(app = app)
    }
}