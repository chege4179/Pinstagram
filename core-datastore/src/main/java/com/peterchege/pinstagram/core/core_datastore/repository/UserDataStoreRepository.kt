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
package com.peterchege.pinstagram.core.core_datastore.repository

import android.content.Context
import androidx.datastore.dataStore
import com.peterchege.pinstagram.core.core_datastore.serializers.UserInfoSerializer
import com.peterchege.pinstagram.core.core_model.external_models.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


val Context.userDataStore by dataStore("user.json", UserInfoSerializer)
class UserDataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
){

    suspend fun getLoggedInUser(): Flow<User?> {
        return context.userDataStore.data
    }
    suspend fun setLoggedInUser(user: User) {
        context.userDataStore.updateData {
            user
        }
    }
    suspend fun unsetLoggedInUser() {
        context.userDataStore.updateData {
            null
        }
    }

}