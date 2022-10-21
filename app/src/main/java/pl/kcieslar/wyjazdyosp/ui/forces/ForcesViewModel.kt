package pl.kcieslar.wyjazdyosp.ui.forces

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kcieslar.wyjazdyosp.data.MainDatabase
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.Equipment
import pl.kcieslar.wyjazdyosp.model.Fireman
import pl.kcieslar.wyjazdyosp.model.Forces
import pl.kcieslar.wyjazdyosp.repository.CarRepository
import pl.kcieslar.wyjazdyosp.Repository.EquipmentRepository
import pl.kcieslar.wyjazdyosp.repository.FiremanRepository
import pl.kcieslar.wyjazdyosp.ui.forces.ForcesDataType

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

    fun addItem(forcesType: ForcesDataType, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (forcesType) {
                ForcesDataType.CAR -> carRepository.addCar(Car(0, name))
                ForcesDataType.FIREMAN -> firemanRepository.addFireman(Fireman(0, name))
                ForcesDataType.EQUIPMENT -> equipmentRepository.addEquipment(Equipment(0, name))
            }
        }
    }

    fun editItem(forcesType: ForcesDataType, itemId: Int, name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (forcesType) {
                ForcesDataType.CAR -> carRepository.editCar(Car(itemId, name))
                ForcesDataType.FIREMAN -> firemanRepository.editFireman(Fireman(itemId, name))
                ForcesDataType.EQUIPMENT -> equipmentRepository.editEquipment(Equipment(itemId, name))
            }
        }
    }

    fun removeItem(item: Forces) {
        viewModelScope.launch(Dispatchers.IO) {
            when (item) {
                is Car -> carRepository.removeCar(item)
                is Fireman -> firemanRepository.removeFireman(item)
                is Equipment -> equipmentRepository.removeEquipment(item)
            }
        }
    }
}