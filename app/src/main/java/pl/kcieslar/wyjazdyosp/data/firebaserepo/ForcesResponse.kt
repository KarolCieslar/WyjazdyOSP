package pl.kcieslar.wyjazdyosp.data.firebaserepo

import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.Equipment
import pl.kcieslar.wyjazdyosp.model.Fireman

data class ForcesResponse (
    var firemanList: List<Fireman> = emptyList(),
    var carList: List<Car> = emptyList(),
    var equipmentList: List<Equipment> = emptyList(),
    var exception: Exception? = null
)