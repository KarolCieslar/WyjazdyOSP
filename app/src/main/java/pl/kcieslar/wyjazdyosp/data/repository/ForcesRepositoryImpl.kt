package pl.kcieslar.wyjazdyosp.data.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import pl.kcieslar.wyjazdyosp.data.firebaserepo.ForcesResponse
import pl.kcieslar.wyjazdyosp.domain.repository.ForcesRepository
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.Equipment
import pl.kcieslar.wyjazdyosp.model.Fireman
import javax.inject.Inject

class ForcesRepositoryImpl @Inject constructor() : ForcesRepository {

    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val forcesRef: DatabaseReference = rootRef.child("forces")

    override suspend fun getForces(): ForcesResponse {
        val response = ForcesResponse()
        try {
            response.firemanList = forcesRef.child("firemans").get().await().children.map { snapShot ->
                val item = snapShot.getValue(Fireman::class.java)!!
                item.key = snapShot.key!!
                item
            }
            response.carList = forcesRef.child("cars").get().await().children.map { snapShot ->
                val item = snapShot.getValue(Car::class.java)!!
                item.key = snapShot.key!!
                item
            }
            response.equipmentList = forcesRef.child("equipments").get().await().children.map { snapShot ->
                val item = snapShot.getValue(Equipment::class.java)!!
                item.key = snapShot.key!!
                item
            }
        } catch (exception: Exception) {
            response.exception = exception
        }
        return response
    }
}