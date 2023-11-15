package pl.kcieslar.wyjazdyosp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import pl.kcieslar.wyjazdyosp.data.retrofit.StatusApi
import pl.kcieslar.wyjazdyosp.data.service.StatusService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object RetrofitModule {

    @Provides
    @ViewModelScoped
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://kcieslar.pl/wyjazdyosp/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Statuss
    @Provides
    @ViewModelScoped
    fun provideStatusApi(retrofit: Retrofit): StatusApi {
        return retrofit.create(StatusApi::class.java)
    }

    @Provides
    @ViewModelScoped
    fun provideStatusService(statusApi: StatusApi): StatusService {
        return StatusService(statusApi)
    }

}