package pl.kcieslar.wyjazdyosp.domain.repository

import pl.kcieslar.wyjazdyosp.data.firebaserepo.FirebaseCallResponse
import pl.kcieslar.wyjazdyosp.data.firebaserepo.FiremanResponse
import pl.kcieslar.wyjazdyosp.data.firebaserepo.ForcesResponse
import pl.kcieslar.wyjazdyosp.model.Fireman

interface ForcesRepository {

    suspend fun getForces() : ForcesResponse
}