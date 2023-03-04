package pl.kcieslar.wyjazdyosp.data.repository

import androidx.lifecycle.LiveData
import pl.kcieslar.wyjazdyosp.data.dao.FiremanDao
import pl.kcieslar.wyjazdyosp.domain.repository.FiremanRepository
import pl.kcieslar.wyjazdyosp.model.Action
import pl.kcieslar.wyjazdyosp.model.Fireman
import javax.inject.Inject

class FiremanRepositoryImpl @Inject constructor(
    private val firemanDao: FiremanDao
) : FiremanRepository {

    val getAllFiremans: LiveData<List<Fireman>> = firemanDao.getAllFiremans()
    val getAllFiremanActions: LiveData<List<Action>> = firemanDao.getAllFiremanActions()

    override suspend fun addFireman(fireman: Fireman) {
        firemanDao.addFireman(fireman)
    }

    override suspend fun editFireman(fireman: Fireman) {
        firemanDao.editFireman(fireman)
    }

    override suspend fun removeFireman(fireman: Fireman) {
        firemanDao.removeFireman(fireman)
    }
}