package pl.kcieslar.wyjazdyosp.repository

import androidx.lifecycle.LiveData
import pl.kcieslar.wyjazdyosp.data.FiremanDao
import pl.kcieslar.wyjazdyosp.model.Action
import pl.kcieslar.wyjazdyosp.model.Fireman
import javax.inject.Inject

class FiremanRepository @Inject constructor(
    private val firemanDao: FiremanDao
) {

    val getAllFiremans: LiveData<List<Fireman>> = firemanDao.getAllFiremans()
    val getAllFiremanActions: LiveData<List<Action>> = firemanDao.getAllFiremanActions()

    suspend fun addFireman(fireman: Fireman) {
        firemanDao.addFireman(fireman)
    }

    suspend fun editFireman(fireman: Fireman) {
        firemanDao.editFireman(fireman)
    }

    suspend fun removeFireman(fireman: Fireman) {
        firemanDao.removeFireman(fireman)
    }
}