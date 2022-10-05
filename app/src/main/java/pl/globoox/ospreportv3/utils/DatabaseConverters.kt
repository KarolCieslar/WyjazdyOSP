package pl.globoox.ospreportv3.utils

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.CarInAction
import pl.globoox.ospreportv3.model.Equipment
import pl.globoox.ospreportv3.model.Fireman
import java.lang.reflect.Type
import java.time.LocalDateTime

class DatabaseConverters {

    @TypeConverter
    fun carToString(car: Car): String {
        return Gson().toJson(car)
    }

    @TypeConverter
    fun stringToCar(carString: String): Car {
        val objectType = object : TypeToken<Car>() {}.type
        return Gson().fromJson(carString, objectType)
    }

    @TypeConverter
    fun carInActionToString(carInAction: CarInAction): String {
        return Gson().toJson(carInAction)
    }

    @TypeConverter
    fun stringToCarInAction(carString: String): CarInAction {
        val objectType = object : TypeToken<CarInAction>() {}.type
        return Gson().fromJson(carString, objectType)
    }

    @TypeConverter
    fun listOfCarInActionToString(value: List<CarInAction>?) = Gson().toJson(value)

    @TypeConverter
    fun stringListOfToCarInAction(value: String) = Gson().fromJson(value, Array<CarInAction>::class.java).toList()

    @TypeConverter
    fun listOfEquipmentsToString(value: List<Equipment>?) = Gson().toJson(value)

    @TypeConverter
    fun stringListOfToEquipments(value: String) = Gson().fromJson(value, Array<Equipment>::class.java).toList()
}