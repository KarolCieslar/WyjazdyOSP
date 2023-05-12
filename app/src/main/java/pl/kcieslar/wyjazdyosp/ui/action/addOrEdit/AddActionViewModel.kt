package pl.kcieslar.wyjazdyosp.ui.action.addOrEdit

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kcieslar.wyjazdyosp.data.firebaserepo.FirebaseCallResponse
import pl.kcieslar.wyjazdyosp.data.repository.ActionRepositoryImpl
import pl.kcieslar.wyjazdyosp.data.repository.ForcesRepositoryImpl
import pl.kcieslar.wyjazdyosp.model.Action
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.Equipment
import pl.kcieslar.wyjazdyosp.mvvm.RefreshableLiveData
import pl.kcieslar.wyjazdyosp.mvvm.SingleLiveEvent
import pl.kcieslar.wyjazdyosp.utils.ViewModelEvent
import javax.inject.Inject

@HiltViewModel
class AddActionViewModel @Inject constructor(
    private val forcesRepository: ForcesRepositoryImpl,
    private val actionRepository: ActionRepositoryImpl,
) : ViewModel() {

    private var _viewModelEvents = SingleLiveEvent<ViewModelEvent>()
    val viewModelEvents: LiveData<ViewModelEvent>
        get() = _viewModelEvents

    init {
        _viewModelEvents.value = LoadingData()
    }

    var selectedCarsList: MutableLiveData<List<Car>> = MutableLiveData()

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

    fun setSelectedCars(list: List<Car>) {
        selectedCarsList.postValue(list)
    }

    fun setSelectedEquipments(list: List<Equipment>) {
        action = action.copy(equipment = list)
    }

    var isEditMode: Boolean = false

    lateinit var action: Action

    fun addAction(action: Action) {
        viewModelScope.launch {
            val response = actionRepository.addAction(action)
            if (response.isSuccess) {
                _viewModelEvents.value = ActionAddedSuccessfully()
            } else {
                _viewModelEvents.value = CallBackError(response.exception)
            }
        }
    }

    fun editAction(action: Action) {
        // Sonar
    }

    inner class LoadingData : ViewModelEvent()
    inner class ActionAddedSuccessfully : ViewModelEvent()
    inner class CallBackError(val exception: Exception?) : ViewModelEvent()
}