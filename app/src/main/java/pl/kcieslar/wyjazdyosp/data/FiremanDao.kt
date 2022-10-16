package pl.kcieslar.wyjazdyosp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import pl.kcieslar.wyjazdyosp.model.Action
import pl.kcieslar.wyjazdyosp.model.Fireman

@Dao
interface FiremanDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFireman(vararg users: Fireman)

    @Update
    suspend fun editFireman(fireman: Fireman)

    @Delete
    suspend fun removeFireman(fireman: Fireman)

    @Query("SELECT * FROM fireman ORDER BY id ASC")
    fun getAllFiremans(): LiveData<List<Fireman>>

    @Query("SELECT * FROM actions ORDER BY id ASC")
    fun getAllFiremanActions(): LiveData<List<Action>>
}