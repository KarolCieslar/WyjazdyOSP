package pl.globoox.ospreportv3.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.globoox.ospreportv3.model.Fireman
import pl.globoox.ospreportv3.data.MainDatabase
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

    fun addCar(car: Car) {
        viewModelScope.launch(Dispatchers.IO) {
            carRepository.addCar(car)
        }
    }

    fun editCar(car: Car) {
        viewModelScope.launch(Dispatchers.IO) {
            carRepository.editCar(car)
        }
    }

    fun removeCar(car: Car) {
        viewModelScope.launch(Dispatchers.IO) {
            carRepository.removeCar(car)
        }
    }

    fun addFireman(fireman: Fireman) {
        viewModelScope.launch(Dispatchers.IO) {
            firemanRepository.addFireman(fireman)
        }
    }

    fun editFireman(fireman: Fireman) {
        viewModelScope.launch(Dispatchers.IO) {
            firemanRepository.editFireman(fireman)
        }
    }

    fun removeFireman(fireman: Fireman) {
        viewModelScope.launch(Dispatchers.IO) {
            firemanRepository.removeFireman(fireman)
        }
    }

    fun addEquipment(equipment: Equipment) {
        viewModelScope.launch(Dispatchers.IO) {
            equipmentRepository.addEquipment(equipment)
        }
    }

    fun editEquipment(equipment: Equipment) {
        viewModelScope.launch(Dispatchers.IO) {
            equipmentRepository.editEquipment(equipment)
        }
    }

    fun removeEquipment(equipment: Equipment) {
        viewModelScope.launch(Dispatchers.IO) {
            equipmentRepository.removeEquipment(equipment)
        }
    }
}