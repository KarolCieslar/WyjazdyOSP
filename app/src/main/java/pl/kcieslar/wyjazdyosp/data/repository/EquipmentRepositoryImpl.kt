package pl.kcieslar.wyjazdyosp.data.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import pl.kcieslar.wyjazdyosp.data.firebaserepo.FirebaseCallResponse
import pl.kcieslar.wyjazdyosp.data.firebaserepo.EquipmentResponse
import pl.kcieslar.wyjazdyosp.domain.repository.EquipmentRepository
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.Equipment
import javax.inject.Inject

class EquipmentRepositoryImpl @Inject constructor() : EquipmentRepository {

    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val forcesRef: DatabaseReference = rootRef.child("forces/equipments")

    override suspend fun getEquipments(): EquipmentResponse {
        val response = EquipmentResponse()
        try {
            response.list = forcesRef.get().await().children.map { snapShot ->
                val item = snapShot.getValue(Equipment::class.java)!!
                item.key = snapShot.key!!
                item
            }
        } catch (exception: Exception) {
            response.exception = exception
        }
        return response
    }

    override suspend fun addEquipment(equipment: Equipment) : FirebaseCallResponse {
        return try {
            forcesRef.child(equipment.key).setValue(equipment).await()
            FirebaseCallResponse(true, null)
        } catch (exception: Exception) {
            FirebaseCallResponse(false, exception)
        }
    }

    override suspend fun editEquipment(equipment: Equipment): FirebaseCallResponse {
        return try {
            forcesRef.child(equipment.key).setValue(equipment).await()
            FirebaseCallResponse(true, null)
        } catch (exception: Exception) {
            FirebaseCallResponse(false, exception)
        }
    }

    override suspend fun removeEquipment(equipment: Equipment): FirebaseCallResponse {
        return try {
            forcesRef.child(equipment.key).setValue(null).await()
            FirebaseCallResponse(true, null)
        } catch (exception: Exception) {
            FirebaseCallResponse(false, exception)
        }
    }
}