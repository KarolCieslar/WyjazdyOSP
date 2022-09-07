package pl.globoox.ospreportv3.repository

import androidx.lifecycle.LiveData
import pl.globoox.ospreportv3.data.ActionDao
import pl.globoox.ospreportv3.model.Action

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