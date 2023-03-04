package pl.kcieslar.wyjazdyosp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.kcieslar.wyjazdyosp.data.dao.ActionDao
import pl.kcieslar.wyjazdyosp.data.dao.CarDao
import pl.kcieslar.wyjazdyosp.data.dao.EquipmentDao
import pl.kcieslar.wyjazdyosp.data.dao.FiremanDao
import pl.kcieslar.wyjazdyosp.model.Action
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.Equipment
import pl.kcieslar.wyjazdyosp.model.Fireman
import pl.kcieslar.wyjazdyosp.utils.DatabaseConverters

@Database(entities=[Action::class, Car::class, Fireman::class, Equipment::class], version = 1, exportSchema = false)
@TypeConverters(DatabaseConverters::class)
abstract class MainDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao
    abstract fun firemanDao(): FiremanDao
    abstract fun equipmentDao(): EquipmentDao
    abstract fun actionDao(): ActionDao
}