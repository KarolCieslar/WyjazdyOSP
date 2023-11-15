package pl.kcieslar.wyjazdyosp.data.retrofit

import pl.kcieslar.wyjazdyosp.model.Group
import retrofit2.Response
import retrofit2.http.GET

interface StatusApi {
    @GET("/get.php")
    suspend fun getGroups(): Response<List<Group>>
}