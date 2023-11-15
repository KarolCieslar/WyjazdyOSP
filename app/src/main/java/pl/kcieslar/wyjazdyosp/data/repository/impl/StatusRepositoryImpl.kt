package pl.kcieslar.wyjazdyosp.data.repository.impl

import pl.kcieslar.wyjazdyosp.data.repository.domain.StatusRepository
import pl.kcieslar.wyjazdyosp.data.service.StatusService
import pl.kcieslar.wyjazdyosp.model.Group
import javax.inject.Inject

class StatusRepositoryImpl @Inject constructor(private val statusService: StatusService) : StatusRepository {
    override suspend fun getGroups(): List<Group> {
        return statusService.getGroups()
    }
}