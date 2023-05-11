package pl.kcieslar.wyjazdyosp.data.repository

import androidx.lifecycle.LiveData
import pl.kcieslar.wyjazdyosp.data.dao.ActionDao
import pl.kcieslar.wyjazdyosp.domain.repository.ActionRepository
import pl.kcieslar.wyjazdyosp.model.Action
import javax.inject.Inject

class ActionRepositoryImpl @Inject constructor(
    private val actionDao: ActionDao
) : ActionRepository {

    val getAllActions: LiveData<List<Action>?> = actionDao.getAllActions()

    override suspend fun addAction(action: Action) {
        actionDao.addAction(action)
    }

    override suspend fun editAction(action: Action) {
        actionDao.editAction(action)
    }

    override suspend fun removeAction(action: Action) {
        actionDao.removeAction(action)
    }
}