package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap

@Parcelize
data class Action(
    var key: String = "",
    val outTime: String = "",
    val inTime: String = "",
    val location: String = "",
    val number: String = "",
    val description: String = "",
    val carsInAction: List<CarInAction> = emptyList(),
    val equipment: List<Equipment> = emptyList(),
) : Parcelable {
    @Exclude
    fun getFormattedOutTime(): String {
        val dateFormatterHelper: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.ROOT)
        return outTime.format(dateFormatterHelper)
    }

    @Exclude
    fun getFormattedInTime(): String {
        val dateFormatterHelper: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.ROOT)
        return inTime.format(dateFormatterHelper)
    }

    @Exclude
    fun convertToHashMap() : HashMap<String, Any?> {
        return hashMapOf(
            "outTime" to outTime,
            "inTime" to inTime,
            "location" to location,
            "number" to number,
            "description" to description,
            "carsInAction" to carsInAction.map { it.convertToHashMap() },
            "equipment" to equipment.map { it.convertToHashMap() },
        )
    }
}