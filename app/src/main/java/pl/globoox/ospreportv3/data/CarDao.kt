package pl.globoox.ospreportv3.data

import androidx.lifecycle.LiveData
import androidx.room.*
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.Fireman


@Dao
interface CarDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCar(vararg users: Car)

    @Update
    suspend fun editCar(car: Car)

    @Delete
    suspend fun removeCar(car: Car)

    @Query("SELECT * FROM cars_table ORDER BY id ASC")
    fun getAllCars(): LiveData<List<Car>>
}