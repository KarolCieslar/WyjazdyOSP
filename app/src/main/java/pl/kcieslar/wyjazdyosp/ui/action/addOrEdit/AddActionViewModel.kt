package pl.kcieslar.wyjazdyosp.ui.action.addOrEdit

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.kcieslar.wyjazdyosp.data.response.ForcesResponse
import pl.kcieslar.wyjazdyosp.data.repository.ActionRepositoryImpl
import pl.kcieslar.wyjazdyosp.data.repository.ForcesRepositoryImpl
import pl.kcieslar.wyjazdyosp.model.Action
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.Equipment
import pl.kcieslar.wyjazdyosp.mvvm.SingleLiveEvent
import pl.kcieslar.wyjazdyosp.mvvm.ViewModelEvent
import javax.inject.Inject

@HiltViewModel
class AddActionViewModel @Inject constructor(
    private val forcesRepository: ForcesRepositoryImpl,
    private val actionRepository: ActionRepositoryImpl,
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

    var selectedCarsList: MutableLiveData<List<Car>> = MutableLiveData()

    fun refreshData() {
        _viewModelEvents.value = LoadingData()
        observeForcesList()
    }

    fun setSelectedCars(list: List<Car>) {
        selectedCarsList.postValue(list)
    }

    fun setSelectedEquipments(list: List<Equipment>) {
        action = action.copy(equipment = list)
    }

    var isEditMode: Boolean = false

    lateinit var action: Action

    fun addAction() {
        viewModelScope.launch {
            val response = actionRepository.addAction(action.convertToHashMap())
            if (response.isSuccess) {
                _viewModelEvents.value = ActionAddedOrEditedSuccessfully()
            } else {
                _viewModelEvents.value = ErrorWithAddOrEditAction(response.exception)
            }
        }
    }

    fun editAction() {
        viewModelScope.launch {
            val response = actionRepository.editAction(action.key, action.convertToHashMap())
            if (response.isSuccess) {
                _viewModelEvents.value = ActionAddedOrEditedSuccessfully()
            } else {
                _viewModelEvents.value = ErrorWithAddOrEditAction(response.exception)
            }
        }
    }

    inner class LoadingData : ViewModelEvent()
    inner class ActionAddedOrEditedSuccessfully : ViewModelEvent()
    inner class ErrorWithAddOrEditAction(val exception: Exception?) : ViewModelEvent()
}