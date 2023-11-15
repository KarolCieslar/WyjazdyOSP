package pl.kcieslar.wyjazdyosp.ui.salary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.kcieslar.wyjazdyosp.base.BaseViewModel
import pl.kcieslar.wyjazdyosp.data.repository.impl.ActionRepositoryImpl
import pl.kcieslar.wyjazdyosp.data.repository.impl.ForcesRepositoryImpl
import pl.kcieslar.wyjazdyosp.data.response.ActionResponse
import pl.kcieslar.wyjazdyosp.data.response.ForcesResponse
import javax.inject.Inject

@HiltViewModel
class SalaryViewModel @Inject constructor(
    private val forcesRepository: ForcesRepositoryImpl,
    private val actionRepository: ActionRepositoryImpl
) : BaseViewModel() {

    private val _actionList = MutableLiveData<ActionResponse>()
    val actions: LiveData<ActionResponse>
        get() = _actionList

    private val _forcesList = MutableLiveData<ForcesResponse>()
    val forces: LiveData<ForcesResponse>
        get() = _forcesList

    init {
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

}