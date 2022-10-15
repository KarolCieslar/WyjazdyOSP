package pl.kcieslar.wyjazdyosp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import pl.kcieslar.wyjazdyosp.model.Car


@Dao
interface CarDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCar(vararg users: Car)

    @Update
    suspend fun editCar(car: Car)

    @Delete
    suspend fun removeCar(car: Car)

    @Query("SELECT * FROM cars ORDER BY id ASC")
    fun getAllCars(): LiveData<List<Car>>
}