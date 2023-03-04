package pl.kcieslar.wyjazdyosp.domain.repository

import pl.kcieslar.wyjazdyosp.model.Action

interface ActionRepository {

    suspend fun addAction(action: Action)
    suspend fun editAction(action: Action)
    suspend fun removeAction(action: Action)
}