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
import pl.globoox.ospreportv3.repository.CarRepository
import pl.globoox.ospreportv3.repository.EquipmentRepository
import pl.globoox.ospreportv3.repository.FiremanRepository

class ForcesViewModel(application: Application) : AndroidViewModel(application) {

    val carList: LiveData<List<Car>>
    private val carRepository: CarRepository

    val firemanList: LiveData<List<Fireman>>
    private val firemanRepository: FiremanRepository

    val equipmentList: LiveData<List<Equipment>>
    private val equipmentRepository: EquipmentRepository


    init {
        val database = MainDatabase.getFiremansDatabase(application)
        carRepository = CarRepository(database.carDao())
        carList = carRepository.getAllCars

        firemanRepository = FiremanRepository(database.firemanDao())
        firemanList = firemanRepository.getAllFiremans

        equipmentRepository = EquipmentRepository(database.equipmentDao())
        equipmentList = equipmentRepository.getAllEquipment
    }

    fun addItem(item: Any) {
        viewModelScope.launch(Dispatchers.IO) {
            when (item) {
                is Car -> carRepository.addCar(item)
                is Fireman -> firemanRepository.addFireman(item)
                is Equipment -> equipmentRepository.addEquipment(item)
            }
        }
    }

    fun editItem(item: Any) {
        viewModelScope.launch(Dispatchers.IO) {
            when (item) {
                is Car -> carRepository.editCar(item)
                is Fireman -> firemanRepository.editFireman(item)
                is Equipment -> equipmentRepository.editEquipment(item)
            }
        }
    }

    fun removeItem(item: Any) {
        viewModelScope.launch(Dispatchers.IO) {
            when (item) {
                is Car -> carRepository.removeCar(item)
                is Fireman -> firemanRepository.removeFireman(item)
                is Equipment -> equipmentRepository.removeEquipment(item)
            }
        }
    }
}