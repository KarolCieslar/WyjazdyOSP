package pl.globoox.ospreportv3.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.Equipment
import pl.globoox.ospreportv3.model.Fireman

@Database(entities=[Car::class, Fireman::class, Equipment::class], version = 1, exportSchema = false)
abstract class MainDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao
    abstract fun firemanDao(): FiremanDao
    abstract fun equipmentDao(): EquipmentDao

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
                    "main_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}