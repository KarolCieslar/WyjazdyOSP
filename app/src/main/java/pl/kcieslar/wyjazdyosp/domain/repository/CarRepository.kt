package pl.kcieslar.wyjazdyosp.domain.repository

import pl.kcieslar.wyjazdyosp.data.firebaserepo.FirebaseCallResponse
import pl.kcieslar.wyjazdyosp.data.firebaserepo.CarResponse
import pl.kcieslar.wyjazdyosp.model.Car

interface CarRepository {

    suspend fun getCars() : CarResponse
    suspend fun addCar(car: Car) : FirebaseCallResponse
    suspend fun editCar(car: Car) : FirebaseCallResponse
    suspend fun removeCar(car: Car) : FirebaseCallResponse
}