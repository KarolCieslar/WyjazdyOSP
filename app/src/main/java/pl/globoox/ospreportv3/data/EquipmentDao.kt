package pl.globoox.ospreportv3.data

import androidx.lifecycle.LiveData
import androidx.room.*
import pl.globoox.ospreportv3.model.Equipment


@Dao
interface EquipmentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addEquipment(vararg users: Equipment)

    @Update
    suspend fun editEquipment(equipment: Equipment)

    @Delete
    suspend fun removeEquipment(equipment: Equipment)

    @Query("SELECT * FROM equipments ORDER BY id ASC")
    fun getAllEquipments(): LiveData<List<Equipment>>
}