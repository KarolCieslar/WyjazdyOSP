package pl.kcieslar.wyjazdyosp.utils

import android.util.Log
import androidx.room.TypeConverter
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.CarInAction
import pl.kcieslar.wyjazdyosp.model.Equipment

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
    fun stringListOfToCarInAction(value: String): List<CarInAction> {
        return try {
            val list = Gson().fromJson(value, Array<CarInAction>::class.java)
            list.toList()
        } catch (e: Exception) {
            val single = Gson().fromJson(value, CarInAction::class.java)
            Firebase.crashlytics.log("DatabaseConverters Crash with JSON value: $value")
            Log.e("DatabaseConverters", "stringListOfToCarInAction: ", e)
            listOf(single)
        }
    }

    @TypeConverter
    fun listOfEquipmentsToString(value: List<Equipment>?) = Gson().toJson(value)

    @TypeConverter
    fun stringListOfToEquipments(value: String) = Gson().fromJson(value, Array<Equipment>::class.java).toList()
}