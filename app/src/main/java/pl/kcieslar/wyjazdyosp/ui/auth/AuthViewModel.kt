package pl.kcieslar.wyjazdyosp.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.kcieslar.wyjazdyosp.base.BaseViewModel
import pl.kcieslar.wyjazdyosp.data.repository.impl.AuthRepositoryImpl
import pl.kcieslar.wyjazdyosp.mvvm.SingleLiveEvent
import pl.kcieslar.wyjazdyosp.mvvm.ViewModelEvent
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl
) : BaseViewModel() {

    private val TAG = "MainViewModel"

    private val _viewModelEvents = MutableLiveData<SingleLiveEvent<ViewModelEvent?>>()
    val viewModelEvents: LiveData<SingleLiveEvent<ViewModelEvent?>> get() = _viewModelEvents

    fun login(email: String, password: String) = viewModelScope.launch {
        try {
            val user = authRepository.signInWithEmailPassword(email, password)
            user?.let {
                _viewModelEvents.value = SingleLiveEvent(LoginSuccessful())
            }
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(TAG, "login: ${error[1]}")
            _viewModelEvents.value = SingleLiveEvent(OperationError(error[1]))
        }
    }

    fun register(email: String, password: String) = viewModelScope.launch {
        try {
            val user = authRepository.signUpWithEmailPassword(email, password)
            user?.let {
                _viewModelEvents.value = SingleLiveEvent(RegisterSuccessful())
            }
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(TAG, "register: ${error[1]}")
            _viewModelEvents.value = SingleLiveEvent(OperationError(error[1]))
        }
    }

    fun logout() = viewModelScope.launch {
        try {
            val user = authRepository.signOut()
            if (user == null) {
                _viewModelEvents.value = SingleLiveEvent(LogoutSuccessful())
            } else {
                _viewModelEvents.value = SingleLiveEvent(OperationError("Error with logout"))
            }
            getCurrentUser()
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(TAG, "logout: ${error[1]}")
            _viewModelEvents.value = SingleLiveEvent(OperationError(error[1]))
        }
    }

    fun getCurrentUser() = authRepository.getUser()

    fun sendPasswordResetEmail(email: String) = viewModelScope.launch {
        try {
            val result = authRepository.sendPasswordReset(email)
            if (result) {
                _viewModelEvents.value = SingleLiveEvent(ResetPasswordSend())
            } else {
                _viewModelEvents.value = SingleLiveEvent(OperationError("could not send password reset"))
            }
        } catch (e: Exception) {
            val error = e.toString().split(":").toTypedArray()
            Log.d(TAG, "sendPasswordResetEmail: ${error[1]}")
            _viewModelEvents.value = SingleLiveEvent(OperationError(error[1]))
        }
    }

    inner class ResetPasswordSend : ViewModelEvent()
    inner class LoginSuccessful : ViewModelEvent()
    inner class RegisterSuccessful : ViewModelEvent()
    inner class LogoutSuccessful : ViewModelEvent()
    inner class OperationError(val error: String, val retryAction: (() -> Unit)? = null) : ViewModelEvent()
}
