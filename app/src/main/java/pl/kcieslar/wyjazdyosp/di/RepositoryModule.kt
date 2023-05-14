package pl.kcieslar.wyjazdyosp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.kcieslar.wyjazdyosp.data.repository.*
import pl.kcieslar.wyjazdyosp.domain.repository.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindActionRepository(actionRepositoryImpl: ActionRepositoryImpl): ActionRepository

    @Binds
    @Singleton
    abstract fun bindForcesRepository(forcesRepository: ForcesRepositoryImpl): ForcesRepository
}