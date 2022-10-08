package pl.globoox.ospreportv3.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.globoox.ospreportv3.model.*
import pl.globoox.ospreportv3.utils.DatabaseConverters

@Database(entities=[Action::class, Car::class, Fireman::class, Equipment::class], version = 1, exportSchema = false)
@TypeConverters(DatabaseConverters::class)
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
                    "main_database_10"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}