package pl.globoox.ospreportv3.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.globoox.ospreportv3.data.MainDatabase
import pl.globoox.ospreportv3.model.*
import pl.globoox.ospreportv3.repository.ActionRepository
import pl.globoox.ospreportv3.repository.CarRepository
import pl.globoox.ospreportv3.repository.EquipmentRepository
import pl.globoox.ospreportv3.repository.FiremanRepository

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