package pl.globoox.ospreportv3.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.Fireman


@Dao
interface CarDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCar(vararg users: Car)

    @Query("SELECT * FROM cars_table ORDER BY id ASC")
    fun getAllCars(): LiveData<List<Car>>
}