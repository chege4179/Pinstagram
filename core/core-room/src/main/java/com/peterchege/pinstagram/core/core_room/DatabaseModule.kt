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
package com.peterchege.pinstagram.core.core_room

import android.app.Application
import androidx.room.Room
import com.peterchege.pinstagram.core.core_common.Constants
import com.peterchege.pinstagram.core.core_common.IoDispatcher
import com.peterchege.pinstagram.core.core_room.database.PinstagramDatabase
import com.peterchege.pinstagram.core.core_room.repository.MediaAssetRepository
import com.peterchege.pinstagram.core.core_room.repository.MediaAssetRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePinstagramDatabase(app: Application):PinstagramDatabase {
        return Room.databaseBuilder(
            app,
            PinstagramDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMediaAssetRepository(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        db:PinstagramDatabase,
    ):MediaAssetRepository{
        return MediaAssetRepositoryImpl(db = db,ioDispatcher = ioDispatcher)
    }
}