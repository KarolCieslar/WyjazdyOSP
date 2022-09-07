package pl.globoox.ospreportv3.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.globoox.ospreportv3.model.*
import pl.globoox.ospreportv3.utils.CarConverter
import pl.globoox.ospreportv3.utils.CarInActionListConverter
import pl.globoox.ospreportv3.utils.FiremanListConverter

@Database(entities=[Action::class, CarInAction::class, Car::class, Fireman::class, Equipment::class], version = 1, exportSchema = false)
@TypeConverters(FiremanListConverter::class, CarInActionListConverter::class, CarConverter::class)
abstract class MainDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao
    abstract fun firemanDao(): FiremanDao
    abstract fun equipmentDao(): EquipmentDao
    abstract fun actionDao(): ActionDao

    companion object {
        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getFiremansDatabase(context: Context): MainDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "main_database_dwa"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}