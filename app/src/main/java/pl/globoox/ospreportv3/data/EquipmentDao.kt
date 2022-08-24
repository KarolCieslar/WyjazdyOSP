package pl.globoox.ospreportv3.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.globoox.ospreportv3.model.Equipment
import pl.globoox.ospreportv3.model.Fireman


@Dao
interface EquipmentDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addEquipment(vararg users: Equipment)

    @Query("SELECT * FROM equipments_table ORDER BY id ASC")
    fun getAllEquipments(): LiveData<List<Equipment>>
}