package pl.kcieslar.wyjazdyosp.Repository

import androidx.lifecycle.LiveData
import pl.kcieslar.wyjazdyosp.data.CarDao
import pl.kcieslar.wyjazdyosp.model.Car

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