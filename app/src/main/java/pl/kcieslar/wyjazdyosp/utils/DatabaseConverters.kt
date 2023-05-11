package pl.kcieslar.wyjazdyosp.utils

import androidx.room.TypeConverter
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.gson.Gson
import pl.kcieslar.wyjazdyosp.model.Car
import pl.kcieslar.wyjazdyosp.model.CarInAction
import pl.kcieslar.wyjazdyosp.model.Equipment

class DatabaseConverters {

    @TypeConverter
    fun carToString(car: Car?): String? {
        return Gson().toJson(car)
    }

    @TypeConverter
    fun carInActionToString(carInAction: CarInAction?): String? {
        return Gson().toJson(carInAction)
    }

    @TypeConverter
    fun listOfCarInActionToString(value: List<CarInAction>?) = Gson().toJson(value)

    @TypeConverter
    fun listOfEquipmentsToString(value: List<Equipment>?) = Gson().toJson(value)

    @TypeConverter
    fun stringToCar(carString: String?): Car? {
        FirebaseCrashlytics.getInstance().log("Converter carString: $carString")
        return Gson().fromJson(carString, Car::class.java)
    }

    @TypeConverter
    fun stringToCarInAction(carString: String?): CarInAction? {
        FirebaseCrashlytics.getInstance().log("Converter stringToCarInAction: $carString")
        return Gson().fromJson(carString, CarInAction::class.java)
    }

    @TypeConverter
    fun stringListOfToCarInAction(value: String?): List<CarInAction> {
        FirebaseCrashlytics.getInstance().log("Converter stringListOfToCarInAction: $value")
        return Gson().fromJson(value, Array<CarInAction>::class.java).toList()
    }

    @TypeConverter
    fun stringListOfToEquipments(value: String?): List<Equipment> {
        FirebaseCrashlytics.getInstance().log("Converter stringListOfToEquipments: $value")
        return Gson().fromJson(value, Array<Equipment>::class.java).toList()
    }
}