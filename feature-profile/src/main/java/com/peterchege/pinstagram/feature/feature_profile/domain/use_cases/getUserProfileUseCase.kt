package com.peterchege.pinstagram.feature.feature_profile.domain.use_cases

import android.util.Log
import com.peterchege.pinstagram.core.core_common.Resource
import com.peterchege.pinstagram.core.core_model.response_models.AllPostResponse
import com.peterchege.pinstagram.core.core_model.response_models.GetUserByIdResponse
import com.peterchege.pinstagram.feature.feature_profile.data.ProfileRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: ProfileRepositoryImpl,

    ) {
    operator fun invoke( userId:String ) : Flow<Resource<GetUserByIdResponse>> = flow {
        try {

            emit(Resource.Loading<GetUserByIdResponse>())
            val response = repository.getUserById( userId = userId)
            if (response.success) {
                emit(Resource.Success(response))
            }else{
                emit(Resource.Error<GetUserByIdResponse>( message = "Server error"))
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