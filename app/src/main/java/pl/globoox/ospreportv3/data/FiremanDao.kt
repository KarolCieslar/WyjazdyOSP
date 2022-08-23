package pl.globoox.ospreportv3.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.globoox.ospreportv3.model.Fireman


@Dao
interface FiremanDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFireman(vararg users: Fireman)

    @Query("SELECT * FROM fireman_table ORDER BY id ASC")
    fun getAllFiremans(): LiveData<List<Fireman>>
}