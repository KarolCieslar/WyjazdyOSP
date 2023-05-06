package pl.kcieslar.wyjazdyosp.utils

import androidx.room.TypeConverter
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
    fun carInActionToString(carInAction: CarInAction): String {
        return Gson().toJson(carInAction)
    }

    @TypeConverter
    fun listOfCarInActionToString(value: List<CarInAction>?) = Gson().toJson(value)

    @TypeConverter
    fun listOfEquipmentsToString(value: List<Equipment>?) = Gson().toJson(value)

    @TypeConverter
    fun stringToCar(carString: String): Car {
        val objectType = object : TypeToken<Car>() {}.type
        return Gson().fromJson(carString, objectType)
    }

    @TypeConverter
    fun stringToCarInAction(carString: String): CarInAction {
        val objectType = object : TypeToken<CarInAction>() {}.type
        return Gson().fromJson(carString, objectType)
    }

    @TypeConverter
    fun stringListOfToCarInAction(value: String): List<CarInAction> = Gson().fromJson(value, Array<CarInAction>::class.java).toList()

    @TypeConverter
    fun stringListOfToEquipments(value: String): List<Equipment> = Gson().fromJson(value, Array<Equipment>::class.java).toList()
}