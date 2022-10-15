package pl.kcieslar.wyjazdyosp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.kcieslar.wyjazdyosp.data.MainDatabase
import pl.kcieslar.wyjazdyosp.model.Action
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.Equipment
import pl.kcieslar.wyjazdyosp.model.Fireman
import pl.kcieslar.wyjazdyosp.Repository.ActionRepository
import pl.kcieslar.wyjazdyosp.Repository.CarRepository
import pl.kcieslar.wyjazdyosp.Repository.EquipmentRepository
import pl.kcieslar.wyjazdyosp.Repository.FiremanRepository

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