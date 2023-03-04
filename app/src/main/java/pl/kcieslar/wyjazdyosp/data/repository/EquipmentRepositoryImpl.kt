package pl.kcieslar.wyjazdyosp.data.repository

import androidx.lifecycle.LiveData
import pl.kcieslar.wyjazdyosp.data.dao.EquipmentDao
import pl.kcieslar.wyjazdyosp.domain.repository.EquipmentRepository
import pl.kcieslar.wyjazdyosp.model.Equipment
import javax.inject.Inject

class EquipmentRepositoryImpl @Inject constructor(
    private val equipmentDao: EquipmentDao
) : EquipmentRepository {

    val getAllEquipment: LiveData<List<Equipment>> = equipmentDao.getAllEquipments()

    override suspend fun addEquipment(equipment: Equipment) {
        equipmentDao.addEquipment(equipment)
    }

    override suspend fun editEquipment(equipment: Equipment) {
        equipmentDao.editEquipment(equipment)
    }

    override suspend fun removeEquipment(equipment: Equipment) {
        equipmentDao.removeEquipment(equipment)
    }
}