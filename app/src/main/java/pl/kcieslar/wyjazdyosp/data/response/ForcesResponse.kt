package pl.kcieslar.wyjazdyosp.data.response

import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.Equipment
import pl.kcieslar.wyjazdyosp.model.Fireman
import pl.kcieslar.wyjazdyosp.model.Forces
import pl.kcieslar.wyjazdyosp.ui.forces.ForcesDataType

data class ForcesResponse (
    var forcesList: List<Forces> = emptyList(),
    var exception: Exception? = null
) {
    fun getFiremanList(): List<Fireman> {
        return forcesList.filter { it.type == ForcesDataType.FIREMAN } as List<Fireman>
    }
    fun getCarList(): List<Car> {
        return forcesList.filter { it.type == ForcesDataType.CAR } as List<Car>
    }
    fun getEquipmentList(): List<Equipment> {
        return forcesList.filter { it.type == ForcesDataType.EQUIPMENT } as List<Equipment>
    }
    fun getSpecificTypeList(forcesDataType: ForcesDataType) : List<Forces> {
        return forcesList.filter { it.type == forcesDataType }
    }
}