package pl.globoox.ospreportv3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.globoox.ospreportv3.data.MainDatabase
import pl.globoox.ospreportv3.model.Action
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.Equipment
import pl.globoox.ospreportv3.model.Fireman
import pl.globoox.ospreportv3.repository.ActionRepository
import pl.globoox.ospreportv3.repository.CarRepository
import pl.globoox.ospreportv3.repository.EquipmentRepository
import pl.globoox.ospreportv3.repository.FiremanRepository

class AddActionViewModel(application: Application) : AndroidViewModel(application) {

    val database = MainDatabase.getFiremansDatabase(application)

    private val carRepository: CarRepository = CarRepository(database.carDao())
    val carList: LiveData<List<Car>> = carRepository.getAllCars

    private val firemanRepository: FiremanRepository = FiremanRepository(database.firemanDao())
    val firemanList: LiveData<List<Fireman>> = firemanRepository.getAllFiremans

    private val equipmentRepository: EquipmentRepository = EquipmentRepository(database.equipmentDao())
    val equipmentList: LiveData<List<Equipment>> = equipmentRepository.getAllEquipment

    var selectedCarsList: MutableLiveData<List<Car>> = MutableLiveData()

    fun setSelectedCars(list: List<Car>) {
        selectedCarsList.postValue(list)
    }

    fun setSelectedEquipments(list: List<Equipment>) {
        action = action.copy(equipment = list)
    }

    var primaryButtonAction = {}
    var cancelButtonAction = {}

    private val actionRepository: ActionRepository = ActionRepository(database.actionDao())
    var isEditMode: Boolean = false

    lateinit var action: Action

    fun addAction(action: Action) {
        viewModelScope.launch(Dispatchers.IO) {
            actionRepository.addAction(action)
        }
    }

    fun editAction(action: Action) {
        viewModelScope.launch(Dispatchers.IO) {
            actionRepository.editAction(action)
        }
    }
}