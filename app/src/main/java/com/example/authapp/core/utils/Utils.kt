package com.example.authapp.core.utils

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import com.example.authapp.data.model.response.error.ErrorResponseModel
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

fun <T> safeApiCall(call: suspend () -> Response<T>): Flow<Resource<T>> = flow {
    emit(Resource.Loading())

    var remoteData: Response<T>? = null
    try {
        remoteData = call()


        if (remoteData.isSuccessful) {
            val data = remoteData.body()
            emit(Resource.Success(data))
        } else {
            val gson = GsonBuilder().create()
            val pojo: ErrorResponseModel

            try {
                pojo = gson.fromJson(
                    remoteData.errorBody()?.string(),
                    ErrorResponseModel::class.java
                )
                val errorData =
                    ErrorResponseModel(message = pojo.message)

                Log.e("TAG", "safeApiCall: ${errorData.message}", )
                emit(Resource.Error(errorData.message))
            } catch (e: IOException) {
                Log.e("TAG", "safeApiCall: ${e.message}", )
                emit(
                    Resource.Error(
                        data = remoteData.body(),
                        message = Constants.HttpExceptionError
                    )
                )
            }
        }
    } catch (e: HttpException) {
        Log.e("TAG", "safeApiCall: ${e.message}", )
        emit(
            Resource.Error(
                message = /*e.message ?: */Constants.HttpExceptionError,
                data = null
            )
        )
    } catch (e: IOException) {
        Log.e("TAG", "safeApiCall: ${e.message}", )
        emit(Resource.Error(message = /*e.message ?: */Constants.IOExceptionError, data = null))
    }



}



fun <T> MutableState<T>.asState(): State<T> = this