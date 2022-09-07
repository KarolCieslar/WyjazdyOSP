package pl.globoox.ospreportv3.utils

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import pl.globoox.ospreportv3.model.Action
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.CarInAction
import java.lang.reflect.Type

class CarInActionListConverter {

    @TypeConverter
    fun listToJson(value: List<CarInAction>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<CarInAction>::class.java).toList()
}