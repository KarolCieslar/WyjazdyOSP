package pl.kcieslar.wyjazdyosp.ui.forces

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kcieslar.wyjazdyosp.data.firebaserepo.FirebaseCallResponse
import pl.kcieslar.wyjazdyosp.data.repository.ForcesRepositoryImpl
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.Equipment
import pl.kcieslar.wyjazdyosp.model.Fireman
import pl.kcieslar.wyjazdyosp.model.Forces
import pl.kcieslar.wyjazdyosp.mvvm.RefreshableLiveData
import pl.kcieslar.wyjazdyosp.mvvm.SingleLiveEvent
import pl.kcieslar.wyjazdyosp.mvvm.ViewModelEvent
import javax.inject.Inject

@HiltViewModel
class ForcesViewModel @Inject constructor(
    private val forcesRepository: ForcesRepositoryImpl
) : ViewModel() {

    private var _viewModelEvents = SingleLiveEvent<ViewModelEvent>()
    val viewModelEvents: LiveData<ViewModelEvent>
        get() = _viewModelEvents

    init {
        _viewModelEvents.value = LoadingData()
    }

    val forcesList = RefreshableLiveData {
        liveData(Dispatchers.IO) {
            emit(forcesRepository.getForces())
        }
    }

    fun refreshData() {
        _viewModelEvents.value = LoadingData()
        viewModelScope.launch {
            forcesList.refresh()
        }
    }

    fun addItem(forcesDataType: ForcesDataType, name: String) {
        viewModelScope.launch {
            val item = when (forcesDataType) {
                ForcesDataType.CAR -> Car(name = name)
                ForcesDataType.FIREMAN -> Fireman(name = name)
                ForcesDataType.EQUIPMENT -> Equipment(name = name)
            }
            handleResponse(forcesRepository.addItem(item))
        }
    }

    fun editItem(item: Forces) {
        viewModelScope.launch {
            handleResponse(forcesRepository.editItem(item))
        }
    }

    fun removeItem(item: Forces) {
        viewModelScope.launch {
            handleResponse(forcesRepository.removeItem(item))
        }
    }

    private fun handleResponse(response: FirebaseCallResponse) {
        response.let {
            if (response.isSuccess) {
                _viewModelEvents.value = CallBackSuccessfully()
            } else {
                _viewModelEvents.value = CallBackError(response.exception)
            }
        }
    }

    inner class LoadingData : ViewModelEvent()
    inner class CallBackSuccessfully : ViewModelEvent()
    inner class CallBackError(val exception: Exception?) : ViewModelEvent()
}