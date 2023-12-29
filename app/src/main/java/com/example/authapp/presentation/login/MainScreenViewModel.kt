package com.example.authapp.presentation.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authapp.core.utils.Resource
import com.example.authapp.core.utils.asState
import com.example.authapp.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    private val _authResult = mutableStateOf<Resource<String>>(Resource.Inactive())
    val authResult = _authResult.asState()

    private val _regenerateCode = mutableStateOf<Resource<String>>(Resource.Inactive())
    val regenerateCode = _regenerateCode.asState()


    fun regenerateCode(login: String) {
        viewModelScope.launch {
            repository.regenerateCode(login).onEach {
                _regenerateCode.value = it
            }.launchIn(this)
        }
    }



    fun getCode(login: String) {
        viewModelScope.launch {
            repository.getCode(login).onEach {
                when(it) {
                    is Resource.Success -> {
                        getToken(login, it.data?.code.toString())
                    }
                    is Resource.Error -> {
                        it.message?.let { message ->
                            _authResult.value = Resource.Error(message)
                            coroutineContext.cancel()
                        }
                    }
                    is Resource.Inactive -> {

                    }
                    is Resource.Loading -> {
                        _authResult.value = Resource.Loading()
                    }
                }
            }.launchIn(this)
        }
    }

    private fun getToken(login: String, password: String) {
        viewModelScope.launch {
            repository.getToken(login, password).onEach {
                _authResult.value = it
                if (it is Resource.Error || it is Resource.Success) {
                    coroutineContext.cancel()
                }
            }.launchIn(this)
        }
    }


    fun resetResult() {
        _regenerateCode.value = Resource.Inactive()
    }
}