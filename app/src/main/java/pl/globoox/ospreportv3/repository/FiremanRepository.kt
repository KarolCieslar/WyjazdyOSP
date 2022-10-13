package pl.globoox.ospreportv3.repository

import androidx.lifecycle.LiveData
import pl.globoox.ospreportv3.data.FiremanDao
import pl.globoox.ospreportv3.model.Action
import pl.globoox.ospreportv3.model.Fireman

class FiremanRepository(private val firemanDao: FiremanDao) {

    val getAllFiremans: LiveData<List<Fireman>> = firemanDao.getAllFiremans()
    val getAllFiremanActions: LiveData<List<Action>> = firemanDao.getAllFiremanActions()

    suspend fun addFireman(fireman: Fireman) {
        firemanDao.addFireman(fireman)
    }

    suspend fun editFireman(fireman: Fireman) {
        firemanDao.editFireman(fireman)
    }

    suspend fun removeFireman(fireman: Fireman) {
        firemanDao.removeFireman(fireman)
    }
}