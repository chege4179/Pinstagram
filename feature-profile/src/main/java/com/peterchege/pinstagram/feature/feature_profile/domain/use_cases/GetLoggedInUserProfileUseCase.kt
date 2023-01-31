package com.peterchege.pinstagram.feature.feature_profile.domain.use_cases

import com.peterchege.pinstagram.core.core_common.Resource
import com.peterchege.pinstagram.core.core_model.response_models.GetUserByIdResponse
import com.peterchege.pinstagram.feature.feature_profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoggedInUserProfileUseCase @Inject constructor(
  private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Flow<Resource<GetUserByIdResponse>> {
        return repository.getLoggedInUserById()
    }
}