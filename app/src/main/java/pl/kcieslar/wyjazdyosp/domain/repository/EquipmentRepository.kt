package pl.kcieslar.wyjazdyosp.domain.repository

import pl.kcieslar.wyjazdyosp.model.Equipment

interface EquipmentRepository {

    suspend fun addEquipment(equipment: Equipment)
    suspend fun editEquipment(equipment: Equipment)
    suspend fun removeEquipment(equipment: Equipment)
}