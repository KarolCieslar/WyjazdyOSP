package pl.kcieslar.wyjazdyosp.ui.action.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kcieslar.wyjazdyosp.data.repository.ActionRepositoryImpl
import pl.kcieslar.wyjazdyosp.model.*
import pl.kcieslar.wyjazdyosp.mvvm.RefreshableLiveData
import pl.kcieslar.wyjazdyosp.mvvm.SingleLiveEvent
import pl.kcieslar.wyjazdyosp.ui.action.addOrEdit.stepThird.FiremanFunction
import pl.kcieslar.wyjazdyosp.ui.forces.ForcesDataType
import pl.kcieslar.wyjazdyosp.utils.ViewModelEvent
import javax.inject.Inject

@HiltViewModel
class ActionListViewModel @Inject constructor(
    private val actionRepository: ActionRepositoryImpl
) : ViewModel() {

    private var _viewModelEvents = SingleLiveEvent<ViewModelEvent>()
    val viewModelEvents: LiveData<ViewModelEvent>
        get() = _viewModelEvents

    init {
        _viewModelEvents.value = LoadingData()
    }

    val actionList = RefreshableLiveData {
        liveData(Dispatchers.IO) {
            emit(actionRepository.getActions())
        }
    }

    fun refreshData() {
        _viewModelEvents.value = LoadingData()
        viewModelScope.launch {
            actionList.refresh()
        }
    }

    fun removeAction(action: Action) {
        viewModelScope.launch {
            actionRepository.removeAction(action)
        }
    }

    inner class LoadingData : ViewModelEvent()
    inner class CallBackSuccessfully : ViewModelEvent()
    inner class CallBackError(val exception: Exception?) : ViewModelEvent()
}