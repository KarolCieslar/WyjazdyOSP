package pl.globoox.ospreportv3.utils

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import pl.globoox.ospreportv3.model.CarInAction
import pl.globoox.ospreportv3.model.Fireman
import java.lang.reflect.Type

class FiremanListConverter {

    @TypeConverter
    fun listToJson(value: List<Fireman>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Fireman>::class.java).toList()
}