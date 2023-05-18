package pl.kcieslar.wyjazdyosp.ui.forces

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.kcieslar.wyjazdyosp.data.response.ForcesResponse
import pl.kcieslar.wyjazdyosp.data.repository.ForcesRepositoryImpl
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.Equipment
import pl.kcieslar.wyjazdyosp.model.Fireman
import pl.kcieslar.wyjazdyosp.model.Forces
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

    private val _forcesList = MutableLiveData<ForcesResponse>()
    val forces: LiveData<ForcesResponse>
        get() = _forcesList

    init {
        _viewModelEvents.value = LoadingData()
        observeForcesList()
    }

    private fun observeForcesList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                forcesRepository.getForces().collect {
                    _forcesList.postValue(it)
                }
            }
        }
    }

    fun refreshData() {
        _viewModelEvents.value = LoadingData()
        observeForcesList()
    }

    fun addItem(forcesDataType: ForcesDataType, name: String) {
        viewModelScope.launch {
            val item = when (forcesDataType) {
                ForcesDataType.CAR -> Car(name = name)
                ForcesDataType.FIREMAN -> Fireman(name = name)
                ForcesDataType.EQUIPMENT -> Equipment(name = name)
            }
            val response = forcesRepository.addItem(item)
            if (response.isSuccess) {
                _viewModelEvents.value = CrudItemSuccessfully()
            } else {
                _viewModelEvents.value = CrudItemError(response.exception) { addItem(forcesDataType, name) }
            }
        }
    }

    fun editItem(item: Forces) {
        viewModelScope.launch {
            val response = forcesRepository.editItem(item)
            if (response.isSuccess) {
                _viewModelEvents.value = CrudItemSuccessfully()
            } else {
                _viewModelEvents.value = CrudItemError(response.exception) { editItem(item) }
            }
        }
    }

    fun removeItem(item: Forces) {
        viewModelScope.launch {
            val response = forcesRepository.removeItem(item)
            if (response.isSuccess) {
                _viewModelEvents.value = CrudItemSuccessfully()
            } else {
                _viewModelEvents.value = CrudItemError(response.exception) { removeItem(item) }
            }
        }
    }

    inner class LoadingData : ViewModelEvent()
    inner class CrudItemSuccessfully : ViewModelEvent()
    inner class CrudItemError(val exception: Exception?, val retryAction: (() -> Unit)) : ViewModelEvent()
}