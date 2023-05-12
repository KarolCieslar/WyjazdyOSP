package pl.kcieslar.wyjazdyosp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.kcieslar.wyjazdyosp.model.Action
import pl.kcieslar.wyjazdyosp.utils.DatabaseConverters

@Database(entities=[Action::class], version = 1, exportSchema = false)
@TypeConverters(DatabaseConverters::class)
abstract class MainDatabase : RoomDatabase() {
}