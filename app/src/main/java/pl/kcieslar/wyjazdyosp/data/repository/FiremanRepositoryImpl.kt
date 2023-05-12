package pl.kcieslar.wyjazdyosp.data.repository

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import pl.kcieslar.wyjazdyosp.data.firebaserepo.FirebaseCallResponse
import pl.kcieslar.wyjazdyosp.data.firebaserepo.FiremanResponse
import pl.kcieslar.wyjazdyosp.domain.repository.FiremanRepository
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.Equipment
import pl.kcieslar.wyjazdyosp.model.Fireman
import javax.inject.Inject

class FiremanRepositoryImpl @Inject constructor() : FiremanRepository {

    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val forcesRef: DatabaseReference = rootRef.child("forces/firemans")

    override suspend fun getFiremans(): FiremanResponse {
        val response = FiremanResponse()
        try {
            response.list = forcesRef.get().await().children.map { snapShot ->
                val item = snapShot.getValue(Fireman::class.java)!!
                item.key = snapShot.key!!
                item
            }
        } catch (exception: Exception) {
            response.exception = exception
        }
        return response
    }

    override suspend fun addFireman(fireman: Fireman) : FirebaseCallResponse {
        return try {
            forcesRef.child(fireman.key).setValue(fireman).await()
            FirebaseCallResponse(true, null)
        } catch (exception: Exception) {
            FirebaseCallResponse(false, exception)
        }
    }

    override suspend fun editFireman(fireman: Fireman): FirebaseCallResponse {
        return try {
            forcesRef.child(fireman.key).setValue(fireman).await()
            FirebaseCallResponse(true, null)
        } catch (exception: Exception) {
            FirebaseCallResponse(false, exception)
        }
    }

    override suspend fun removeFireman(fireman: Fireman): FirebaseCallResponse {
        return try {
            forcesRef.child(fireman.key).setValue(null).await()
            FirebaseCallResponse(true, null)
        } catch (exception: Exception) {
            FirebaseCallResponse(false, exception)
        }
    }
}