package pl.kcieslar.wyjazdyosp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.kcieslar.wyjazdyosp.data.repository.domain.ActionRepository
import pl.kcieslar.wyjazdyosp.data.repository.domain.AuthRepository
import pl.kcieslar.wyjazdyosp.data.repository.domain.FirebaseLogService
import pl.kcieslar.wyjazdyosp.data.repository.domain.ForcesRepository
import pl.kcieslar.wyjazdyosp.data.repository.domain.StatusRepository
import pl.kcieslar.wyjazdyosp.data.repository.impl.ActionRepositoryImpl
import pl.kcieslar.wyjazdyosp.data.repository.impl.AuthRepositoryImpl
import pl.kcieslar.wyjazdyosp.data.repository.impl.FirebaseLogServiceImpl
import pl.kcieslar.wyjazdyosp.data.repository.impl.ForcesRepositoryImpl
import pl.kcieslar.wyjazdyosp.data.repository.impl.StatusRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindStatusRepository(statusRepository: StatusRepositoryImpl): StatusRepository

    @Binds
    @Singleton
    abstract fun bindFirebaseLogService(firebaseLogService: FirebaseLogServiceImpl): FirebaseLogService
}