package pl.kcieslar.wyjazdyosp.data.repository

import androidx.lifecycle.LiveData
import pl.kcieslar.wyjazdyosp.domain.repository.ActionRepository
import pl.kcieslar.wyjazdyosp.model.Action
import javax.inject.Inject

class ActionRepositoryImpl @Inject constructor() : ActionRepository {

   // val getAllActions: LiveData<List<Action>?> = actionDao.getAllActions()

    override suspend fun addAction(action: Action) {
        // Sonar
    }

    override suspend fun editAction(action: Action) {
        // Sonar
    }

    override suspend fun removeAction(action: Action) {
        // Sonar
    }
}