package pl.globoox.ospreportv3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import pl.globoox.ospreportv3.data.MainDatabase
import pl.globoox.ospreportv3.model.Action
import pl.globoox.ospreportv3.model.Fireman
import pl.globoox.ospreportv3.repository.FiremanRepository

class SalaryViewModel(application: Application) : AndroidViewModel(application) {

    val firemanList: LiveData<List<Fireman>>
    val firemanActions: LiveData<List<Action>>
    private val firemanRepository: FiremanRepository
    var dateButtonSelected = false

    init {
        val database = MainDatabase.getFiremansDatabase(application)
        firemanRepository = FiremanRepository(database.firemanDao())
        firemanList = firemanRepository.getAllFiremans
        firemanActions = firemanRepository.getAllFiremanActions
    }
}