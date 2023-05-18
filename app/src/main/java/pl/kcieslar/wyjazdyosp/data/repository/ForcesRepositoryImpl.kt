package pl.kcieslar.wyjazdyosp.data.repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import pl.kcieslar.wyjazdyosp.data.response.FirebaseCallResponse
import pl.kcieslar.wyjazdyosp.data.response.ForcesResponse
import pl.kcieslar.wyjazdyosp.domain.repository.ForcesRepository
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.Equipment
import pl.kcieslar.wyjazdyosp.model.Fireman
import pl.kcieslar.wyjazdyosp.model.Forces
import pl.kcieslar.wyjazdyosp.ui.forces.ForcesDataType
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ForcesRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepositoryImpl
) : ForcesRepository {

    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val forcesRef: DatabaseReference = rootRef.child(authRepository.getUser()!!.uid).child("forces")

    override fun getForces(): Flow<ForcesResponse?> {
        val response = ForcesResponse()
        return callbackFlow {
            val listener = forcesRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    try {
                        Log.d("ForcesRepositoryImpl", "onDataChange: $dataSnapshot")
                        val list = mutableListOf<Forces>()
                        dataSnapshot.children.map { snapShot ->
                            val item = when (snapShot.child("type").value) {
                                ForcesDataType.FIREMAN.name -> snapShot.getValue(Fireman::class.java)!!
                                ForcesDataType.CAR.name -> snapShot.getValue(Car::class.java)!!
                                ForcesDataType.EQUIPMENT.name -> snapShot.getValue(Equipment::class.java)!!
                                else -> throw IllegalArgumentException("Unknown forces type")
                            }
                            item.key = snapShot.key!!
                            list.add(item)
                        }
                        response.forcesList = list
                        trySend(response)
                    } catch (exception: Exception) {
                        response.exception = exception
                        trySend(response)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    response.exception = Exception(error.message)
                    cancel()
                }
            })
            awaitClose { forcesRef.removeEventListener(listener) }
        }
    }

    override suspend fun addItem(item: Forces): FirebaseCallResponse {
        return try {
            val itemMap = HashMap<String, String>()
            itemMap["name"] = item.name
            itemMap["type"] = item.type.name
            forcesRef.push().setValue(itemMap).await()
            FirebaseCallResponse(true, null)
        } catch (exception: Exception) {
            FirebaseCallResponse(false, exception)
        }
    }

    override suspend fun editItem(item: Forces): FirebaseCallResponse {
        return try {
            val itemMap = HashMap<String, String>()
            itemMap["name"] = item.name
            itemMap["type"] = item.type.name
            forcesRef.child(item.key).setValue(itemMap).await()
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