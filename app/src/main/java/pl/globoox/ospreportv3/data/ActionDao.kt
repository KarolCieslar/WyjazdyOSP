package pl.globoox.ospreportv3.data

import androidx.lifecycle.LiveData
import androidx.room.*
import pl.globoox.ospreportv3.model.Action
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.Fireman


@Dao
interface ActionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAction(vararg actions: Action)

    @Update
    suspend fun editAction(action: Action)

    @Delete
    suspend fun removeAction(action: Action)

    @Query("SELECT * FROM actions ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun getAllActions(limit: Int, offset: Int): LiveData<List<Action>>
}