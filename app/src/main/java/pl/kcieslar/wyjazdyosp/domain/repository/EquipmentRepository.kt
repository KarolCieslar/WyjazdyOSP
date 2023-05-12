package pl.kcieslar.wyjazdyosp.domain.repository

import pl.kcieslar.wyjazdyosp.data.firebaserepo.FirebaseCallResponse
import pl.kcieslar.wyjazdyosp.data.firebaserepo.EquipmentResponse
import pl.kcieslar.wyjazdyosp.model.Equipment

interface EquipmentRepository {

    suspend fun getEquipments() : EquipmentResponse
    suspend fun addEquipment(equipment: Equipment) : FirebaseCallResponse
    suspend fun editEquipment(equipment: Equipment) : FirebaseCallResponse
    suspend fun removeEquipment(equipment: Equipment) : FirebaseCallResponse
}