package pl.kcieslar.wyjazdyosp.ui.action.addOrEdit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.kcieslar.wyjazdyosp.model.Action
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.Equipment
import pl.kcieslar.wyjazdyosp.data.repository.ActionRepositoryImpl
import pl.kcieslar.wyjazdyosp.data.repository.CarRepositoryImpl
import pl.kcieslar.wyjazdyosp.data.repository.EquipmentRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class AddActionViewModel @Inject constructor(
    private val carRepository: CarRepositoryImpl,
    private val equipmentRepository: EquipmentRepositoryImpl,
    private val actionRepository: ActionRepositoryImpl
) : ViewModel() {

  //  val carList: LiveData<List<Car>> = carRepository.getAllCars
//    val firemanList: LiveData<List<Fireman>> = firemanRepository.getFiremans()
    //val equipmentList: LiveData<List<Equipment>> = equipmentRepository.getAllEquipment
    var selectedCarsList: MutableLiveData<List<Car>> = MutableLiveData()

    fun setSelectedCars(list: List<Car>) {
        selectedCarsList.postValue(list)
    }

    fun setSelectedEquipments(list: List<Equipment>) {
        action = action.copy(equipment = list)
    }

    var primaryButtonAction = {
        // Sonar
    }
    var cancelButtonAction = {
        // Sonar
    }

    var isEditMode: Boolean = false

    lateinit var action: Action

    fun addAction(action: Action) {
        viewModelScope.launch {
            actionRepository.addAction(action)
        }
    }

    fun editAction(action: Action) {
        viewModelScope.launch {
            actionRepository.editAction(action)
        }
    }
}