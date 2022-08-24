package pl.globoox.ospreportv3.repository

import androidx.lifecycle.LiveData
import pl.globoox.ospreportv3.data.CarDao
import pl.globoox.ospreportv3.data.EquipmentDao
import pl.globoox.ospreportv3.model.Fireman
import pl.globoox.ospreportv3.data.FiremanDao
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.Equipment

class EquipmentRepository(private val equipmentDao: EquipmentDao) {

    val getAllEquipment: LiveData<List<Equipment>> = equipmentDao.getAllEquipments()

    suspend fun addEquipment(equipment: Equipment) {
        equipmentDao.addEquipment(equipment)
    }
}