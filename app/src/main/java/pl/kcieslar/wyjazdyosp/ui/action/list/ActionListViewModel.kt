package pl.kcieslar.wyjazdyosp.ui.action.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.kcieslar.wyjazdyosp.data.repository.ActionRepositoryImpl
import pl.kcieslar.wyjazdyosp.model.*
import pl.kcieslar.wyjazdyosp.ui.action.addOrEdit.stepThird.FiremanFunction
import javax.inject.Inject

@HiltViewModel
class ActionListViewModel @Inject constructor(
    private val actionRepository: ActionRepositoryImpl
) : ViewModel() {

    val actionList: LiveData<List<Action>?> = actionRepository.getAllActions

    fun removeAction(action: Action) {
        viewModelScope.launch {
            actionRepository.removeAction(action)
        }
    }

    fun addMultipleActions() {
        val actions = mutableListOf<Action>()
        repeat(50) {
            val time = "${(10..30).random()}.06.2023"
            val action = Action(
                id = it,
                outTime = "$time 12:20",
                inTime = "$time 14:50",
                location = "dd.MM.yyyy HH:mm",
                number = "Numer akcji $it",
                description = "Opis akcji numer $it",
                carsInAction = createCarsInAction(),
                equipment = createEquipmentsInAction()
            )
            actions.add(action)
        }
        actions.forEach {
            viewModelScope.launch {
                actionRepository.addAction(it)
            }
        }
    }

    private fun createCarsInAction(): MutableList<CarInAction> {
        val carsInAction = mutableListOf<CarInAction>()
        repeat((1..2).random()) {
            val carInAction = CarInAction(
                car = Car(
                    id = it,
                    name = "Samochód $it",
                ),
                firemans = createFiremans()
            )
            carsInAction.add(carInAction)
        }
        return carsInAction
    }

    private fun createEquipmentsInAction(): MutableList<Equipment> {
        val equipmentInAction = mutableListOf<Equipment>()
        repeat((0..2).random()) {
            val equipment = Equipment(
                id = it,
                name = "Sprzęt $it"
            )
            equipmentInAction.add(equipment)
        }
        return equipmentInAction
    }

    private fun createFiremans(): MutableList<Fireman> {
        val firemanList = mutableListOf<Fireman>()
        repeat((1..3).random()) {
            val fireman = Fireman(
                id = it,
                name = "Strażak $it",
            )
            fireman.selectStatus = (0..1).random()
            val test = (0..1).random()
            fireman.functions[0] = if (test == 0) { mutableListOf(FiremanFunction.DRIVER, FiremanFunction.OWNCAR)} else { mutableListOf() }
            firemanList.add(fireman)
        }
        return firemanList
    }
}