package pl.kcieslar.wyjazdyosp.repository

import androidx.lifecycle.LiveData
import pl.kcieslar.wyjazdyosp.data.CarDao
import pl.kcieslar.wyjazdyosp.model.Car
import javax.inject.Inject

class CarRepository @Inject constructor(
    private val carDao: CarDao
) {

    val getAllCars: LiveData<List<Car>> = carDao.getAllCars()
    val carsCount: LiveData<Int> = carDao.carsCount()

    suspend fun addCar(car: Car) {
        carDao.addCar(car)
    }

    suspend fun editCar(car: Car) {
        carDao.editCar(car)
    }

    suspend fun removeCar(car: Car) {
        carDao.removeCar(car)
    }
}