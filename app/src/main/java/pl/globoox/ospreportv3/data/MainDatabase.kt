package pl.globoox.ospreportv3.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.globoox.ospreportv3.model.Fireman

@Database(entities = [Fireman::class], version = 1, exportSchema = false)
abstract class MainDatabase: RoomDatabase() {
    abstract fun firemanDao() : FiremanDao

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
                    "fireman_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}