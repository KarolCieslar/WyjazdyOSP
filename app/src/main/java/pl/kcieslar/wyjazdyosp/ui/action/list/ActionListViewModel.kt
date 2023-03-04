package pl.kcieslar.wyjazdyosp.ui.action.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.kcieslar.wyjazdyosp.model.Action
import pl.kcieslar.wyjazdyosp.data.repository.ActionRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class ActionListViewModel @Inject constructor(
    private val application: Application,
    private val actionRepository: ActionRepositoryImpl
) : AndroidViewModel(application) {

    val actionList: LiveData<List<Action>> = actionRepository.getAllActions

    fun removeAction(action: Action) {
        viewModelScope.launch {
            actionRepository.removeAction(action)
        }
    }
}