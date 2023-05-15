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
import pl.kcieslar.wyjazdyosp.data.firebaserepo.ActionResponse
import pl.kcieslar.wyjazdyosp.data.firebaserepo.FirebaseCallResponse
import pl.kcieslar.wyjazdyosp.domain.repository.ActionRepository
import pl.kcieslar.wyjazdyosp.model.Action
import javax.inject.Inject

class ActionRepositoryImpl @Inject constructor() : ActionRepository {

    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val actionRef: DatabaseReference = rootRef.child("actions")

    override fun getActions(): Flow<ActionResponse?> {
        val response = ActionResponse()
        return callbackFlow {
            val listener = actionRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    try {
                        Log.d("ForcesRepositoryImpl", "onDataChange: $dataSnapshot")
                        val list = mutableListOf<Action>()
                        dataSnapshot.children.map { snapShot ->
                            val item = snapShot.getValue(Action::class.java)!!
                            item.key = snapShot.key!!
                            list.add(item)
                        }
                        response.list = list
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
            awaitClose { actionRef.removeEventListener(listener) }
        }
    }

    override suspend fun addAction(action: Action) : FirebaseCallResponse {
        return try {
            actionRef.child(action.key).setValue(action).await()
            FirebaseCallResponse(true, null)
        } catch (exception: Exception) {
            FirebaseCallResponse(false, exception)
        }
    }

    override suspend fun editAction(action: Action) : FirebaseCallResponse {
        return FirebaseCallResponse(true, null)
    }

    override suspend fun removeAction(action: Action) : FirebaseCallResponse {
        return FirebaseCallResponse(true, null)
    }
}