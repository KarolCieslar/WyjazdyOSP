package pl.kcieslar.wyjazdyosp.domain.repository

import androidx.lifecycle.LiveData
import pl.kcieslar.wyjazdyosp.data.dao.EquipmentDao
import pl.kcieslar.wyjazdyosp.model.Equipment
import javax.inject.Inject

class EquipmentRepository @Inject constructor(
    private val equipmentDao: EquipmentDao
) {

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