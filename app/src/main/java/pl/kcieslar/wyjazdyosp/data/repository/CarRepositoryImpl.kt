package pl.kcieslar.wyjazdyosp.data.repository

import androidx.lifecycle.LiveData
import pl.kcieslar.wyjazdyosp.data.dao.CarDao
import pl.kcieslar.wyjazdyosp.domain.repository.CarRepository
import pl.kcieslar.wyjazdyosp.model.Car
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(
    private val carDao: CarDao
) : CarRepository {

    val getAllCars: LiveData<List<Car>> = carDao.getAllCars()
    val carsCount: LiveData<Int> = carDao.carsCount()

    override suspend fun addCar(car: Car) {
        carDao.addCar(car)
    }

    override suspend fun editCar(car: Car) {
        carDao.editCar(car)
    }

    override suspend fun removeCar(car: Car) {
        carDao.removeCar(car)
    }
}