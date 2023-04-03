package pl.kcieslar.wyjazdyosp.ui.forces

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.Equipment
import pl.kcieslar.wyjazdyosp.model.Fireman
import pl.kcieslar.wyjazdyosp.model.Forces
import pl.kcieslar.wyjazdyosp.repository.CarRepository
import pl.kcieslar.wyjazdyosp.repository.EquipmentRepository
import pl.kcieslar.wyjazdyosp.repository.FiremanRepository
import javax.inject.Inject

@HiltViewModel
class ForcesViewModel @Inject constructor(
    private val application: Application,
    private val carRepository: CarRepository,
    private val firemanRepository: FiremanRepository,
    private val equipmentRepository: EquipmentRepository,
) : AndroidViewModel(application) {

    val carList: LiveData<List<Car>> = carRepository.getAllCars
    val firemanList: LiveData<List<Fireman>> = firemanRepository.getAllFiremans
    val equipmentList: LiveData<List<Equipment>> = equipmentRepository.getAllEquipment

    fun addItem(forcesType: ForcesDataType, name: String) {
        viewModelScope.launch {
            when (forcesType) {
                ForcesDataType.CAR -> carRepository.addCar(Car(0, name))
                ForcesDataType.FIREMAN -> firemanRepository.addFireman(Fireman(0, name))
                ForcesDataType.EQUIPMENT -> equipmentRepository.addEquipment(Equipment(0, name))
            }
        }
    }

    fun editItem(forcesType: ForcesDataType, itemId: Int, name: String) {
        viewModelScope.launch {
            when (forcesType) {
                ForcesDataType.CAR -> carRepository.editCar(Car(itemId, name))
                ForcesDataType.FIREMAN -> firemanRepository.editFireman(Fireman(itemId, name))
                ForcesDataType.EQUIPMENT -> equipmentRepository.editEquipment(Equipment(itemId, name))
            }
        }
    }

    fun removeItem(item: Forces) {
        viewModelScope.launch {
            when (item) {
                is Car -> carRepository.removeCar(item)
                is Fireman -> firemanRepository.removeFireman(item)
                is Equipment -> equipmentRepository.removeEquipment(item)
            }
        }
    }
}