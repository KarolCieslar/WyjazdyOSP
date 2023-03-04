package pl.kcieslar.wyjazdyosp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.kcieslar.wyjazdyosp.data.repository.ActionRepositoryImpl
import pl.kcieslar.wyjazdyosp.data.repository.CarRepositoryImpl
import pl.kcieslar.wyjazdyosp.data.repository.EquipmentRepositoryImpl
import pl.kcieslar.wyjazdyosp.data.repository.FiremanRepositoryImpl
import pl.kcieslar.wyjazdyosp.domain.repository.ActionRepository
import pl.kcieslar.wyjazdyosp.domain.repository.CarRepository
import pl.kcieslar.wyjazdyosp.domain.repository.EquipmentRepository
import pl.kcieslar.wyjazdyosp.domain.repository.FiremanRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindActionRepository(actionRepositoryImpl: ActionRepositoryImpl): ActionRepository

    @Binds
    @Singleton
    abstract fun bindCarRepository(carRepositoryImpl: CarRepositoryImpl): CarRepository

    @Binds
    @Singleton
    abstract fun bindEquipmentRepository(equipmentRepositoryImpl: EquipmentRepositoryImpl): EquipmentRepository

    @Binds
    @Singleton
    abstract fun bindFiremanRepository(firemanRepositoryImpl: FiremanRepositoryImpl): FiremanRepository
}