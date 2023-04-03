package pl.kcieslar.wyjazdyosp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.kcieslar.wyjazdyosp.data.*
import pl.kcieslar.wyjazdyosp.repository.ActionRepository
import pl.kcieslar.wyjazdyosp.repository.CarRepository
import pl.kcieslar.wyjazdyosp.repository.EquipmentRepository
import pl.kcieslar.wyjazdyosp.repository.FiremanRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideMainDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            MainDatabase::class.java,
            "main_database_10"
        ).build()

    @Provides
    @Singleton
    fun provideActionRepository(actionDao: ActionDao): ActionRepository {
        return ActionRepository(actionDao)
    }

    @Provides
    @Singleton
    fun provideCarRepository(carDao: CarDao): CarRepository {
        return CarRepository(carDao)
    }

    @Provides
    @Singleton
    fun provideEquipmentRepository(equipmentDao: EquipmentDao): EquipmentRepository {
        return EquipmentRepository(equipmentDao)
    }

    @Provides
    @Singleton
    fun provideFiremanRepository(firemanDao: FiremanDao): FiremanRepository {
        return FiremanRepository(firemanDao)
    }

    @Provides
    @Singleton
    fun provideActionDao(database: MainDatabase): ActionDao {
        return database.actionDao()
    }

    @Provides
    @Singleton
    fun provideCarDao(database: MainDatabase): CarDao {
        return database.carDao()
    }

    @Provides
    @Singleton
    fun provideEquipmentDao(database: MainDatabase): EquipmentDao {
        return database.equipmentDao()
    }

    @Provides
    @Singleton
    fun provideFiremanDao(database: MainDatabase): FiremanDao {
        return database.firemanDao()
    }
}