package pl.kcieslar.wyjazdyosp.domain.repository

import pl.kcieslar.wyjazdyosp.model.Car

interface CarRepository {

    suspend fun addCar(car: Car)
    suspend fun editCar(car: Car)
    suspend fun removeCar(car: Car)
}