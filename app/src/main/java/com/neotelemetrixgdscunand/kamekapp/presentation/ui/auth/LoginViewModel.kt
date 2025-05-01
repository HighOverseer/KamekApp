package com.neotelemetrixgdscunand.kamekapp.presentation.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.domain.common.Result
import com.neotelemetrixgdscunand.kamekapp.domain.data.AuthRepository
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.util.UIText
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.util.toErrorUIText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
):ViewModel() {

    private var loginJob:Job? = null
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _uiEvent = Channel<LoginUIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun login(
        handphoneNumberOrEmail:String,
        password:String
    ){
        if(loginJob?.isCompleted == false) return

        loginJob = viewModelScope.launch(Dispatchers.IO){
            _isLoading.update { true }

            val isFieldBlank = handphoneNumberOrEmail.trim().isBlank() || password.trim().isBlank()

            if(isFieldBlank){
                val errorUIText = UIText.StringResource(R.string.username_dan_password_tidak_boleh_kosong)
                _uiEvent.send(
                    LoginUIEvent.OnLoginFailed(errorUIText)
                )
                return@launch
            }

            val result = authRepository.login(
                handphoneNumberOrEmail, password
            )
            when(result){
                is Result.Error -> {
                    val errorUIText = result.toErrorUIText()
                    _uiEvent.send(
                        LoginUIEvent.OnLoginFailed(errorUIText)
                    )
                }
                is Result.Success -> {
                    val (userName, isFirstTime) = result.data
                    val firstWordUserName = userName.split(" ").firstOrNull() ?: "Anonim"
                    _uiEvent.send(
                        LoginUIEvent.OnLoginSuccess(firstWordUserName, isFirstTime)
                    )
                }
            }
        }.also {
            it.invokeOnCompletion {
                _isLoading.update { false }
            }
        }
    }

}