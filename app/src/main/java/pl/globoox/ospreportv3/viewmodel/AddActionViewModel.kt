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
import pl.globoox.ospreportv3.utils.CombinedLiveData

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



    private val actionRepository: ActionRepository = ActionRepository(database.actionDao())

    var action = emptyActionObject()
    private fun emptyActionObject(): Action {
        return Action(0, "", "", "", "", "", "", "", emptyList())
    }

    fun addAction(action: Action) {
        viewModelScope.launch(Dispatchers.IO) {
            actionRepository.addAction(action)
        }
    }
}