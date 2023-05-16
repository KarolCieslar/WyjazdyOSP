package pl.kcieslar.wyjazdyosp.ui.salary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.kcieslar.wyjazdyosp.data.firebaserepo.ActionResponse
import pl.kcieslar.wyjazdyosp.data.firebaserepo.ForcesResponse
import pl.kcieslar.wyjazdyosp.data.repository.ActionRepositoryImpl
import pl.kcieslar.wyjazdyosp.data.repository.ForcesRepositoryImpl
import pl.kcieslar.wyjazdyosp.model.Action
import pl.kcieslar.wyjazdyosp.mvvm.SingleLiveEvent
import pl.kcieslar.wyjazdyosp.mvvm.ViewModelEvent
import pl.kcieslar.wyjazdyosp.ui.forces.ForcesDataType
import javax.inject.Inject

@HiltViewModel
class SalaryViewModel @Inject constructor(
    private val forcesRepository: ForcesRepositoryImpl,
    private val actionRepository: ActionRepositoryImpl
) : ViewModel() {

    private var _viewModelEvents = SingleLiveEvent<ViewModelEvent>()
    val viewModelEvents: LiveData<ViewModelEvent>
        get() = _viewModelEvents

    private val _actionList = MutableLiveData<ActionResponse>()
    val actions: LiveData<ActionResponse>
        get() = _actionList

    private val _forcesList = MutableLiveData<ForcesResponse>()
    val forces: LiveData<ForcesResponse>
        get() = _forcesList

    init {
        _viewModelEvents.value = LoadingData()
        observeActionList()
        observeForcesList()
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

    private fun observeForcesList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                forcesRepository.getForces().collect {
                    _forcesList.postValue(it)
                }
            }
        }
    }

    inner class RemovingActionInProgress : ViewModelEvent()
    inner class RemovedActionSuccessfully(val action: Action) : ViewModelEvent()
    inner class LoadingData : ViewModelEvent()
    inner class RemovedActionError(val action: Action, val exception: Exception?) : ViewModelEvent()

}