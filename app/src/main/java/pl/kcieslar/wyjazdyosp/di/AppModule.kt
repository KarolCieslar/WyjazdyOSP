package pl.kcieslar.wyjazdyosp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.kcieslar.wyjazdyosp.data.MainDatabase
import pl.kcieslar.wyjazdyosp.model.room.MainDataBackupDao
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
    fun provideMainDao(database: MainDatabase): MainDataBackupDao {
        return database.getMainDao()
    }
}