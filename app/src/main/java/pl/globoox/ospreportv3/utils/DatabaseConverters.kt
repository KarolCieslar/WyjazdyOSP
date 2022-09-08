package pl.globoox.ospreportv3.utils

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.CarInAction
import pl.globoox.ospreportv3.model.Fireman
import java.lang.reflect.Type

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
    fun carInActionListToJson(value: List<CarInAction>?) = Gson().toJson(value)

    @TypeConverter
    fun stringToCarInActionList(value: String) = Gson().fromJson(value, Array<CarInAction>::class.java).toList()

    @TypeConverter
    fun firemanListToJson(value: List<Fireman>?) = Gson().toJson(value)

    @TypeConverter
    fun stringToFiremanList(value: String) = Gson().fromJson(value, Array<Fireman>::class.java).toList()
}