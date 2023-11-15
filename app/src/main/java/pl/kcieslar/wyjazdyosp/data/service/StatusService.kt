package pl.kcieslar.wyjazdyosp.data.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pl.kcieslar.wyjazdyosp.data.retrofit.StatusApi
import pl.kcieslar.wyjazdyosp.model.Group
import javax.inject.Inject

class StatusService @Inject constructor(private val statusApi: StatusApi) {
    suspend fun getGroups(): List<Group> {
        return withContext(Dispatchers.IO) {
            val groups = statusApi.getGroups()
            groups.body() ?: emptyList()
        }
    }
}