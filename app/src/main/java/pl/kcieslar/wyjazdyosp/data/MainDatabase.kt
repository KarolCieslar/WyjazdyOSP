package pl.kcieslar.wyjazdyosp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.kcieslar.wyjazdyosp.model.room.MainDataBackupDao
import pl.kcieslar.wyjazdyosp.model.room.RoomCar

@Database(entities=[RoomCar::class], version = 1, exportSchema = false)
abstract class MainDatabase : RoomDatabase() {
    abstract fun getMainDao(): MainDataBackupDao
}