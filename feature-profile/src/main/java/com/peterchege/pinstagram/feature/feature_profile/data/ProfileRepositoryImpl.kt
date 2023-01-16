package com.peterchege.pinstagram.feature.feature_profile.data

import com.peterchege.pinstagram.core.core_datastore.repository.UserDataStoreRepository
import com.peterchege.pinstagram.core.core_model.response_models.GetUserByIdResponse
import com.peterchege.pinstagram.core.core_network.retrofit.RetrofitPinstagramNetwork
import com.peterchege.pinstagram.feature.feature_profile.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api:RetrofitPinstagramNetwork

): ProfileRepository {
    override suspend fun getUserById(userId: String): GetUserByIdResponse {
        return api.getUserById(userId = userId)
    }

}