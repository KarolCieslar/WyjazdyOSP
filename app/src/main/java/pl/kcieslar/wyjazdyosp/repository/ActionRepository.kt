package pl.kcieslar.wyjazdyosp.Repository

import androidx.lifecycle.LiveData
import pl.kcieslar.wyjazdyosp.data.ActionDao
import pl.kcieslar.wyjazdyosp.model.Action

class ActionRepository(private val actionDao: ActionDao) {

    val getAllActions: LiveData<List<Action>> = actionDao.getAllActions()

    suspend fun addAction(action: Action) {
        actionDao.addAction(action)
    }

    suspend fun editAction(action: Action) {
        actionDao.editAction(action)
    }

    suspend fun removeAction(action: Action) {
        actionDao.removeAction(action)
    }
}