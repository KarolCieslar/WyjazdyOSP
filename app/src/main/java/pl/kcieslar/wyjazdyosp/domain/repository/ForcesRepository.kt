package pl.kcieslar.wyjazdyosp.domain.repository

import kotlinx.coroutines.flow.Flow
import pl.kcieslar.wyjazdyosp.data.response.FirebaseCallResponse
import pl.kcieslar.wyjazdyosp.data.response.ForcesResponse
import pl.kcieslar.wyjazdyosp.model.Forces

interface ForcesRepository {

    fun getForces() : Flow<ForcesResponse?>
    suspend fun addItem(item: Forces) : FirebaseCallResponse
    suspend fun editItem(item: Forces) : FirebaseCallResponse
    suspend fun removeItem(item: Forces) : FirebaseCallResponse
}