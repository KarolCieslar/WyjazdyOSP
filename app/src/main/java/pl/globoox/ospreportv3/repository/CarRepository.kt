package pl.globoox.ospreportv3.repository

import androidx.lifecycle.LiveData
import pl.globoox.ospreportv3.data.CarDao
import pl.globoox.ospreportv3.model.Car

class CarRepository(private val carDao: CarDao) {

    val getAllCars: LiveData<List<Car>> = carDao.getAllCars()

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