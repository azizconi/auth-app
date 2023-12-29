package com.example.authapp.data.repository

import com.example.authapp.core.utils.Resource
import com.example.authapp.core.utils.safeApiCall
import com.example.authapp.data.AuthApi
import com.example.authapp.data.model.response.code.CodeResponseModel
import com.example.authapp.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(private val api: AuthApi): AuthRepository {
    override fun getCode(login: String): Flow<Resource<CodeResponseModel>> = safeApiCall {
        api.getCode(login)
    }

    override fun getToken(login: String, password: String): Flow<Resource<String>> = safeApiCall {
        api.getToken(login, password)
    }

    override fun regenerateCode(login: String): Flow<Resource<String>> = safeApiCall {
        api.regenerateCode(login)
    }
}