package com.peterchege.pinstagram.feature.feature_auth.domain.use_case

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.peterchege.pinstagram.core.core_common.Resource
import com.peterchege.pinstagram.core.core_datastore.UserInfoSerializer
import com.peterchege.pinstagram.core.core_model.external_models.User
import com.peterchege.pinstagram.core.core_model.request_models.LoginBody
import com.peterchege.pinstagram.core.core_model.response_models.LoginResponse
import com.peterchege.pinstagram.feature.feature_auth.data.AuthRepositoryImpl
import com.peterchege.pinstagram.feature.feature_auth.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.intellij.lang.annotations.Language
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


val Context.userDataStore by dataStore("user.json",UserInfoSerializer)

class LoginUseCase @Inject constructor(
    private val repository: AuthRepositoryImpl,
    @ApplicationContext
    private val context: Context
) {
    operator fun invoke(loginUser: LoginBody) : Flow<Resource<LoginResponse>> = flow {
        try {
            emit(Resource.Loading<LoginResponse>())
            val loginResponse = repository.loginUser(loginUser)
            if (loginResponse.success) {
                context.userDataStore.updateData {
                    loginResponse.user
                }
            }
            emit(Resource.Success(loginResponse))
        }catch (e: HttpException){
            emit(Resource.Error<LoginResponse>(e.localizedMessage ?: "Log In failed......Server error"))

        }catch (e: IOException){
            emit(Resource.Error<LoginResponse>("Could not reach server... Please check your internet connection"))

        }
    }
}

