package pl.kcieslar.wyjazdyosp.data.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import pl.kcieslar.wyjazdyosp.data.firebaserepo.FirebaseCallResponse
import pl.kcieslar.wyjazdyosp.data.firebaserepo.ForcesResponse
import pl.kcieslar.wyjazdyosp.domain.repository.ForcesRepository
import pl.kcieslar.wyjazdyosp.model.Fireman
import pl.kcieslar.wyjazdyosp.model.Forces
import javax.inject.Inject

class ForcesRepositoryImpl @Inject constructor() : ForcesRepository {

    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val forcesRef: DatabaseReference = rootRef.child("forces")

    override suspend fun getForces(): ForcesResponse {
        val response = ForcesResponse()
        try {
            response.forcesList = forcesRef.get().await().children.map { snapShot ->
                val item = snapShot.getValue(Fireman::class.java)!!
                item.key = snapShot.key!!
                item
            }
        } catch (exception: Exception) {
            response.exception = exception
        }
        return response
    }

    override suspend fun addItem(item: Forces) : FirebaseCallResponse {
        return try {
            forcesRef.child(item.key).setValue(item).await()
            FirebaseCallResponse(true, null)
        } catch (exception: Exception) {
            FirebaseCallResponse(false, exception)
        }
    }

    override suspend fun editItem(item: Forces): FirebaseCallResponse {
        return try {
            forcesRef.child(item.key).setValue(item).await()
            FirebaseCallResponse(true, null)
        } catch (exception: Exception) {
            FirebaseCallResponse(false, exception)
        }
    }

    override suspend fun removeItem(item: Forces): FirebaseCallResponse {
        return try {
            forcesRef.child(item.key).setValue(null).await()
            FirebaseCallResponse(true, null)
        } catch (exception: Exception) {
            FirebaseCallResponse(false, exception)
        }
    }
}