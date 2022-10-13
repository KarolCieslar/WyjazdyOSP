package pl.globoox.ospreportv3.repository

import androidx.lifecycle.LiveData
import pl.globoox.ospreportv3.data.EquipmentDao
import pl.globoox.ospreportv3.model.Equipment

class EquipmentRepository(private val equipmentDao: EquipmentDao) {

    val getAllEquipment: LiveData<List<Equipment>> = equipmentDao.getAllEquipments()

    suspend fun addEquipment(equipment: Equipment) {
        equipmentDao.addEquipment(equipment)
    }

    suspend fun editEquipment(equipment: Equipment) {
        equipmentDao.editEquipment(equipment)
    }

    suspend fun removeEquipment(equipment: Equipment) {
        equipmentDao.removeEquipment(equipment)
    }
}