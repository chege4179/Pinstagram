package com.peterchege.pinstagram.feature.feature_profile.domain.use_cases

import com.peterchege.pinstagram.core.core_common.Resource
import com.peterchege.pinstagram.core.core_datastore.repository.UserDataStoreRepository
import com.peterchege.pinstagram.core.core_model.response_models.GetUserByIdResponse
import com.peterchege.pinstagram.feature.feature_profile.data.ProfileRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetLoggedInUserProfileUseCase @Inject constructor(
    private val repository: ProfileRepositoryImpl,
    private val userDataStoreRepository: UserDataStoreRepository,

    ) {
    operator fun invoke(  ) : Flow<Resource<GetUserByIdResponse>> = flow {
        try {
            val user = userDataStoreRepository.getLoggedInUser()
            user.collect{
                if (it != null){
                    emit(Resource.Loading<GetUserByIdResponse>())
                    val response = repository.getUserById( userId = it.userId)
                    if (response.success) {
                        emit(Resource.Success(response))
                    }else{
                        emit(Resource.Error<GetUserByIdResponse>( message = "Server error"))
                    }
                }else{
                    emit(Resource.Error<GetUserByIdResponse>( message = "No user"))
                }

            }
        }catch (e: HttpException){
            emit(
                Resource.Error<GetUserByIdResponse>(
                    message = e.localizedMessage ?: "Server error"))

        }catch (e: IOException){
            emit(
                Resource.Error<GetUserByIdResponse>(
                    message = "Could not reach server... Please check your internet connection"))

        }
    }
}