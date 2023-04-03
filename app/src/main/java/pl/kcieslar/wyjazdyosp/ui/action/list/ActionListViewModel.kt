package pl.kcieslar.wyjazdyosp.ui.action.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kcieslar.wyjazdyosp.data.MainDatabase
import pl.kcieslar.wyjazdyosp.model.Action
import pl.kcieslar.wyjazdyosp.repository.ActionRepository

class ActionListViewModel(application: Application) : AndroidViewModel(application) {


    val actionList: LiveData<List<Action>>
    private val actionRepository: ActionRepository

    init {
        val database = MainDatabase.getFiremansDatabase(application)
        actionRepository = ActionRepository(database.actionDao())
        actionList = actionRepository.getAllActions
    }

    fun removeAction(action: Action) {
        viewModelScope.launch(Dispatchers.IO) {
            actionRepository.removeAction(action)
        }
    }
}