package pl.kcieslar.wyjazdyosp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import pl.kcieslar.wyjazdyosp.model.Action


@Dao
interface ActionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAction(vararg actions: Action)

    @Update
    suspend fun editAction(action: Action)

    @Delete
    suspend fun removeAction(action: Action)

    @Query("SELECT * FROM actions ORDER BY id DESC")
    fun getAllActions(): LiveData<List<Action>>
}