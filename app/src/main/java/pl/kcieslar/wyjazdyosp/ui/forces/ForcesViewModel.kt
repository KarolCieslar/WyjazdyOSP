package pl.kcieslar.wyjazdyosp.ui.forces

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kcieslar.wyjazdyosp.data.firebaserepo.FirebaseCallResponse
import pl.kcieslar.wyjazdyosp.data.repository.CarRepositoryImpl
import pl.kcieslar.wyjazdyosp.data.repository.EquipmentRepositoryImpl
import pl.kcieslar.wyjazdyosp.data.repository.FiremanRepositoryImpl
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.Equipment
import pl.kcieslar.wyjazdyosp.model.Fireman
import pl.kcieslar.wyjazdyosp.model.Forces
import pl.kcieslar.wyjazdyosp.mvvm.RefreshableLiveData
import pl.kcieslar.wyjazdyosp.mvvm.SingleLiveEvent
import pl.kcieslar.wyjazdyosp.utils.ViewModelEvent
import pl.kcieslar.wyjazdyosp.utils.generateRandomUUID
import javax.inject.Inject

@HiltViewModel
class ForcesViewModel @Inject constructor(
    private val firemanRepository: FiremanRepositoryImpl,
    private val carRepository: CarRepositoryImpl,
    private val equipmentRepository: EquipmentRepositoryImpl,
) : ViewModel() {

    private var _viewModelEvents = SingleLiveEvent<ViewModelEvent>()
    val viewModelEvents: LiveData<ViewModelEvent>
        get() = _viewModelEvents

    init {
        _viewModelEvents.value = LoadingData()
    }

    val firemanList = RefreshableLiveData {
        liveData(Dispatchers.IO) {
            emit(firemanRepository.getFiremans())
        }
    }

    val carList = RefreshableLiveData {
        liveData(Dispatchers.IO) {
            emit(carRepository.getCars())
        }
    }

    val equipmentList = RefreshableLiveData {
        liveData(Dispatchers.IO) {
            emit(equipmentRepository.getEquipments())
        }
    }

    fun refreshData(forcesType: ForcesDataType) {
        _viewModelEvents.value = LoadingData()
        viewModelScope.launch {
            when (forcesType) {
                ForcesDataType.CAR -> carList.refresh()
                ForcesDataType.FIREMAN -> firemanList.refresh()
                ForcesDataType.EQUIPMENT -> equipmentList.refresh()
            }
        }
    }

    fun addItem(forcesType: ForcesDataType, name: String) {
        viewModelScope.launch {
            val response: FirebaseCallResponse = when (forcesType) {
                ForcesDataType.CAR -> carRepository.addCar(Car(generateRandomUUID(), 0, name))
                ForcesDataType.FIREMAN -> firemanRepository.addFireman(Fireman(generateRandomUUID(), 0, name))
                ForcesDataType.EQUIPMENT -> equipmentRepository.addEquipment(Equipment(generateRandomUUID(), 0, name))
            }
            handleResponse(response)
        }
    }


    fun editItem(item: Forces, newName: String) {
        viewModelScope.launch {
            val response: FirebaseCallResponse = when (item) {
                is Car -> carRepository.editCar(Car(item.key, item.id, newName))
                is Fireman -> firemanRepository.editFireman(Fireman(item.key, item.id, newName))
                is Equipment -> equipmentRepository.editEquipment(Equipment(item.key, item.id, newName))
                else -> FirebaseCallResponse(false, IllegalArgumentException("Unknown type"))
            }
            handleResponse(response)
        }
    }

    fun removeItem(item: Forces) {
        viewModelScope.launch {
            val response: FirebaseCallResponse = when (item) {
                is Car -> carRepository.removeCar(item)
                is Fireman -> firemanRepository.removeFireman(item)
                is Equipment -> equipmentRepository.removeEquipment(item)
                else -> FirebaseCallResponse(false, IllegalArgumentException("Unknown type"))
            }
            handleResponse(response)
        }
    }

    private fun handleResponse(response: FirebaseCallResponse) {
        response.let {
            if (response.isSuccess) {
                _viewModelEvents.value = CallBackSuccessfully()
            } else {
                _viewModelEvents.value = CallBackError(response.exception)
            }
        }
    }

    inner class LoadingData : ViewModelEvent()
    inner class CallBackSuccessfully : ViewModelEvent()
    inner class CallBackError(val exception: Exception?) : ViewModelEvent()
}