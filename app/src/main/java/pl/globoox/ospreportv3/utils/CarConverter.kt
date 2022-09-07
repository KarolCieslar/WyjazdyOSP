package pl.globoox.ospreportv3.utils

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import pl.globoox.ospreportv3.model.Car
import pl.globoox.ospreportv3.model.CarInAction
import pl.globoox.ospreportv3.model.Fireman
import java.lang.reflect.Type

class CarConverter {

    @TypeConverter
    fun recipeToString(car: Car): String {
        return Gson().toJson(car)
    }

    @TypeConverter
    fun stringToRecipe(carString: String): Car {
        val objectType = object : TypeToken<Car>() {}.type
        return Gson().fromJson(carString, objectType)
    }
}