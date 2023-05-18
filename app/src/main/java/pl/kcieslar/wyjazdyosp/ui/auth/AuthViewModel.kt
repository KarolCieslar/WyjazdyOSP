package pl.kcieslar.wyjazdyosp.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.kcieslar.wyjazdyosp.data.repository.AuthRepositoryImpl
import pl.kcieslar.wyjazdyosp.mvvm.SingleLiveEvent
import pl.kcieslar.wyjazdyosp.mvvm.ViewModelEvent
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl
) : ViewModel() {

    private val TAG = "MainViewModel"

    private var _viewModelEvents = SingleLiveEvent<ViewModelEvent>()
    val viewModelEvents: LiveData<ViewModelEvent>
        get() = _viewModelEvents


    fun login(email: String, password: String) = viewModelScope.launch {
        try {
            val user = authRepository.signInWithEmailPassword(email, password)
            user?.let {
                _viewModelEvents.value = LoginSuccessful()
            }
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(TAG, "login: ${error[1]}")
            _viewModelEvents.value = OperationError(error[1])
        }
    }

    fun register(email: String, password: String) = viewModelScope.launch {
        try {
            val user = authRepository.signUpWithEmailPassword(email, password)
            user?.let {
                _viewModelEvents.value = RegisterSuccessful()
            }
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(TAG, "register: ${error[1]}")
            _viewModelEvents.value = OperationError(error[1])
        }
    }

    fun logout() = viewModelScope.launch {
        try {
            val user = authRepository.signOut()
            if (user == null) {
                _viewModelEvents.value = LogoutSuccessful()
            } else {
                _viewModelEvents.value = OperationError("Error with logout")
            }
            getCurrentUser()
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(TAG, "logout: ${error[1]}")
            _viewModelEvents.value = OperationError(error[1])
        }
    }

    fun getCurrentUser() = authRepository.getUser()

    fun sendPasswordResetEmail(email: String) = viewModelScope.launch {
        try {
            val result = authRepository.sendPasswordReset(email)
            if (result) {
                _viewModelEvents.value = ResetPasswordSend()
            } else {
                _viewModelEvents.value = OperationError("could not send password reset")
            }
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(TAG, "sendPasswordResetEmail: ${error[1]}")
            _viewModelEvents.value = OperationError(error[1])
        }
    }

    inner class ResetPasswordSend : ViewModelEvent()
    inner class LoginSuccessful : ViewModelEvent()
    inner class RegisterSuccessful : ViewModelEvent()
    inner class LogoutSuccessful : ViewModelEvent()
    inner class OperationError(val error: String, val retryAction: (() -> Unit)? = null) : ViewModelEvent()
}
