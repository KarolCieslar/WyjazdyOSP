package pl.globoox.ospreportv3.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.globoox.ospreportv3.model.Fireman
import pl.globoox.ospreportv3.data.MainDatabase
import pl.globoox.ospreportv3.model.Action
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.Equipment
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
}