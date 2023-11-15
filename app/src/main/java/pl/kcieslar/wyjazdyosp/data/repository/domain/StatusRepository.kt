package pl.kcieslar.wyjazdyosp.data.repository.domain

import pl.kcieslar.wyjazdyosp.model.Group

interface StatusRepository {

    suspend fun getGroups(): List<Group>
}