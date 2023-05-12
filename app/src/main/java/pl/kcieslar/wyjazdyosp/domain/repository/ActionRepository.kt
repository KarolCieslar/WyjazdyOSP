package pl.kcieslar.wyjazdyosp.domain.repository

import pl.kcieslar.wyjazdyosp.data.firebaserepo.ActionResponse
import pl.kcieslar.wyjazdyosp.data.firebaserepo.FirebaseCallResponse
import pl.kcieslar.wyjazdyosp.model.Action

interface ActionRepository {

    suspend fun getActions(): ActionResponse
    suspend fun addAction(action: Action): FirebaseCallResponse
    suspend fun editAction(action: Action): FirebaseCallResponse
    suspend fun removeAction(action: Action): FirebaseCallResponse
}