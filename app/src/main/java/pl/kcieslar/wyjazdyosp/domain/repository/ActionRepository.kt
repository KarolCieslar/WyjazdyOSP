package pl.kcieslar.wyjazdyosp.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.kcieslar.wyjazdyosp.data.response.ActionResponse
import pl.kcieslar.wyjazdyosp.data.response.FirebaseCallResponse
import pl.kcieslar.wyjazdyosp.model.Action

interface ActionRepository {

    fun getActions(): Flow<ActionResponse?>
    suspend fun addAction(action: HashMap<String, Any?>): FirebaseCallResponse
    suspend fun editAction(actionKey: String, action: HashMap<String, Any?>): FirebaseCallResponse
    suspend fun removeAction(action: Action): FirebaseCallResponse
}