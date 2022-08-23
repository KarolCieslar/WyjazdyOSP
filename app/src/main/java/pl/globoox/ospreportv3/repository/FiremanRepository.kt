package pl.globoox.ospreportv3.repository

import androidx.lifecycle.LiveData
import pl.globoox.ospreportv3.model.Fireman
import pl.globoox.ospreportv3.data.FiremanDao

class FiremanRepository(private val firemanDao: FiremanDao) {

    val getAllFiremans: LiveData<List<Fireman>> = firemanDao.getAllFiremans()

    suspend fun addFireman(fireman: Fireman) {
        firemanDao.addFireman(fireman)
    }
}