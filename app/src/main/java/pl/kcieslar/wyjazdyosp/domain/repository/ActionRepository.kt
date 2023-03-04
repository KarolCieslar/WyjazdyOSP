package pl.kcieslar.wyjazdyosp.data.repository

import pl.kcieslar.wyjazdyosp.model.Action

interface ActionRepository {

    suspend fun addAction(action: Action)
    suspend fun editAction(action: Action)
    suspend fun removeAction(action: Action)
}