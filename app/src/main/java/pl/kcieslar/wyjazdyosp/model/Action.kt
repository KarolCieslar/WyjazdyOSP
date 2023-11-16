package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

@Keep
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

    constructor() : this("key", "outTime", "inTime", "location", "number", "description", listOf(), listOf())

    @Exclude
    fun getFormattedOutTime(): String {
        val dateFormatterHelper: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.ROOT)
        return outTime.format(dateFormatterHelper)
    }

    @Exclude
    fun getOutTimeInUnix(): Long {
        return SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT).parse(outTime)?.time ?: 0
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