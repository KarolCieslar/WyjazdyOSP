package pl.globoox.ospreportv3.viewmodel

import android.app.Application
import androidx.lifecycle.*
import pl.globoox.ospreportv3.model.Fireman
import pl.globoox.ospreportv3.data.MainDatabase
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.Equipment
import pl.globoox.ospreportv3.repository.CarRepository
import pl.globoox.ospreportv3.repository.EquipmentRepository
import pl.globoox.ospreportv3.repository.FiremanRepository

class AddActionViewModel(application: Application) : AndroidViewModel(application) {

    // SaveState - Step 1
    private var cachedOutDate: String? = null
    private var cachedOutTime: String? = null
    private var cachedInDate: String? = null
    private var cachedInTime: String? = null
    private var cachedLocation: String? = null
    private var cachedReportNumber: String? = null
    private var cachedDescription: String? = null


    val database = MainDatabase.getFiremansDatabase(application)

    private val carRepository: CarRepository = CarRepository(database.carDao())
    val carList: LiveData<List<Car>> = carRepository.getAllCars

    private val firemanRepository: FiremanRepository = FiremanRepository(database.firemanDao())
    val firemanList: LiveData<List<Fireman>> = firemanRepository.getAllFiremans

    private val equipmentRepository: EquipmentRepository = EquipmentRepository(database.equipmentDao())
    val equipmentList: LiveData<List<Equipment>> = equipmentRepository.getAllEquipment

    val checkSomeListEmpty = MediatorLiveData<Boolean>()


}