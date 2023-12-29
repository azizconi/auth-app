package com.example.authapp.domain.repository

import com.example.authapp.core.utils.Resource
import com.example.authapp.data.model.response.code.CodeResponseModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getCode(login: String): Flow<Resource<CodeResponseModel>>
    fun getToken(login: String, password: String): Flow<Resource<String>>
    fun regenerateCode(login: String): Flow<Resource<String>>
}