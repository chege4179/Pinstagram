package com.peterchege.pinstagram.feature.feature_profile.domain.repository

import com.peterchege.pinstagram.core.core_model.response_models.GetUserByIdResponse

interface ProfileRepository {

    suspend fun getUserById(userId:String):GetUserByIdResponse



}