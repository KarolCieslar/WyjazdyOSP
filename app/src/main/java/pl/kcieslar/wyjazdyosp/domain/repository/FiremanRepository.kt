package pl.kcieslar.wyjazdyosp.domain.repository

import pl.kcieslar.wyjazdyosp.model.Fireman

interface FiremanRepository {

    suspend fun addFireman(fireman: Fireman)
    suspend fun editFireman(fireman: Fireman)
    suspend fun removeFireman(fireman: Fireman)
}