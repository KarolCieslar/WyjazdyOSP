package pl.globoox.ospreportv3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.globoox.ospreportv3.data.MainDatabase
import pl.globoox.ospreportv3.model.Action
import pl.globoox.ospreportv3.repository.ActionRepository

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