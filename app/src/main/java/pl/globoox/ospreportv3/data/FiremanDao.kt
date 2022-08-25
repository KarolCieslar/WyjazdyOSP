package pl.globoox.ospreportv3.data

import androidx.lifecycle.LiveData
import androidx.room.*
import pl.globoox.ospreportv3.model.Equipment
import pl.globoox.ospreportv3.model.Fireman


@Dao
interface FiremanDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFireman(vararg users: Fireman)

    @Update
    suspend fun editFireman(fireman: Fireman)

    @Delete
    suspend fun removeFireman(fireman: Fireman)

    @Query("SELECT * FROM fireman_table ORDER BY id ASC")
    fun getAllFiremans(): LiveData<List<Fireman>>
}