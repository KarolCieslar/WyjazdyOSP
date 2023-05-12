package pl.kcieslar.wyjazdyosp.domain.repository

import pl.kcieslar.wyjazdyosp.data.firebaserepo.FirebaseCallResponse
import pl.kcieslar.wyjazdyosp.data.firebaserepo.FiremanResponse
import pl.kcieslar.wyjazdyosp.model.Fireman

interface FiremanRepository {

    suspend fun getFiremans() : FiremanResponse
    suspend fun addFireman(fireman: Fireman) : FirebaseCallResponse
    suspend fun editFireman(fireman: Fireman) : FirebaseCallResponse
    suspend fun removeFireman(fireman: Fireman) : FirebaseCallResponse
}