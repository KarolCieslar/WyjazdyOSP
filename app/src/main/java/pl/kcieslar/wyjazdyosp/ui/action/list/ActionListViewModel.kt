package pl.kcieslar.wyjazdyosp.ui.action.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.kcieslar.wyjazdyosp.data.firebaserepo.ActionResponse
import pl.kcieslar.wyjazdyosp.data.firebaserepo.FirebaseCallResponse
import pl.kcieslar.wyjazdyosp.data.repository.ActionRepositoryImpl
import pl.kcieslar.wyjazdyosp.data.repository.ForcesRepositoryImpl
import pl.kcieslar.wyjazdyosp.model.*
import pl.kcieslar.wyjazdyosp.mvvm.SingleLiveEvent
import pl.kcieslar.wyjazdyosp.mvvm.ViewModelEvent
import pl.kcieslar.wyjazdyosp.ui.forces.ForcesDataType
import javax.inject.Inject

@HiltViewModel
class ActionListViewModel @Inject constructor(
    private val actionRepository: ActionRepositoryImpl,
    private val forcesRepository: ForcesRepositoryImpl
) : ViewModel() {

    private var _viewModelEvents = SingleLiveEvent<ViewModelEvent>()
    val viewModelEvents: LiveData<ViewModelEvent>
        get() = _viewModelEvents

    private val _actionList = MutableLiveData<ActionResponse>()
    val actions: LiveData<ActionResponse>
        get() = _actionList

    private val _isAnyCarsAndFiremans = MutableLiveData<Boolean>()
    val isAnyCarsAndFiremans: LiveData<Boolean>
        get() = _isAnyCarsAndFiremans

    init {
        _viewModelEvents.value = LoadingData()
        observeActionList()
        observerCarsAndFiremansCount()
    }

    private fun observeActionList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                actionRepository.getActions().collect {
                    _actionList.postValue(it)
                }
            }
        }
    }

    private fun observerCarsAndFiremansCount() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                forcesRepository.getForces().collect {
                    val firemansCount = it?.forcesList?.any { force -> force.type == ForcesDataType.FIREMAN } ?: false
                    val carsCount = it?.forcesList?.any { force -> force.type == ForcesDataType.CAR } ?: false
                    _isAnyCarsAndFiremans.postValue(firemansCount && carsCount)
                }
            }
        }
    }

    fun refreshData() {
        _viewModelEvents.value = LoadingData()
        observeActionList()
    }

    fun removeAction(action: Action) {
        _viewModelEvents.value = RemovingActionInProgress()
        viewModelScope.launch {
            val response = actionRepository.removeAction(action)
            if (response.isSuccess) {
                _viewModelEvents.value = RemovedActionSuccessfully(action)
            } else {
                _viewModelEvents.value = RemovedActionError(action, response.exception)
            }
        }
    }

    inner class RemovingActionInProgress : ViewModelEvent()
    inner class RemovedActionSuccessfully(val action: Action) : ViewModelEvent()
    inner class LoadingData : ViewModelEvent()
    inner class RemovedActionError(val action: Action, val exception: Exception?) : ViewModelEvent()
}