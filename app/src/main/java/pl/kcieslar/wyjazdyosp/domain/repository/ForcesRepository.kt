package pl.kcieslar.wyjazdyosp.domain.repository

import pl.kcieslar.wyjazdyosp.data.firebaserepo.FirebaseCallResponse
import pl.kcieslar.wyjazdyosp.data.firebaserepo.ForcesResponse
import pl.kcieslar.wyjazdyosp.model.Forces

interface ForcesRepository {

    suspend fun getForces() : ForcesResponse
    suspend fun addItem(item: Forces) : FirebaseCallResponse
    suspend fun editItem(item: Forces) : FirebaseCallResponse
    suspend fun removeItem(item: Forces) : FirebaseCallResponse
}