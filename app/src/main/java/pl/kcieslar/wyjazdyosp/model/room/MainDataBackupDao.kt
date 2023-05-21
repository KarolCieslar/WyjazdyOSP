package pl.kcieslar.wyjazdyosp.model.room

import androidx.room.*

@Dao
interface MainDataBackupDao {
    @Query("SELECT * FROM cars")
    fun getAllCars(): List<RoomCar>
}