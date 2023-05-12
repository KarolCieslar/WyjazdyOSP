package pl.kcieslar.wyjazdyosp.data.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import pl.kcieslar.wyjazdyosp.data.firebaserepo.ActionResponse
import pl.kcieslar.wyjazdyosp.data.firebaserepo.FirebaseCallResponse
import pl.kcieslar.wyjazdyosp.domain.repository.ActionRepository
import pl.kcieslar.wyjazdyosp.model.Action
import javax.inject.Inject

class ActionRepositoryImpl @Inject constructor() : ActionRepository {

    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val forcesRef: DatabaseReference = rootRef.child("actions")

    override suspend fun getActions(): ActionResponse {
        val response = ActionResponse()
        try {
            response.list = forcesRef.get().await().children.map { snapShot ->
                val item = snapShot.getValue(Action::class.java)!!
                item.key = snapShot.key!!
                item
            }
        } catch (exception: Exception) {
            response.exception = exception
        }
        return response
    }

    override suspend fun addAction(action: Action) : FirebaseCallResponse {
        return try {
            forcesRef.child(action.key).setValue(action).await()
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