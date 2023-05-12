package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.format.DateTimeFormatter
import java.util.*

@Parcelize
data class Action(
    var key: String = "",
    val id: Int = -1,
    val outTime: String = "",
    val inTime: String = "",
    val location: String = "",
    val number: String = "",
    val description: String = "",
    val carsInAction: List<CarInAction> = emptyList(),
    val equipment: List<Equipment> = emptyList(),
) : Parcelable {
    fun getFormattedOutTime() : String {
        val dateFormatterHelper: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.ROOT)
        return outTime.format(dateFormatterHelper)
    }
    fun getFormattedInTime() : String {
        val dateFormatterHelper: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.ROOT)
        return inTime.format(dateFormatterHelper)
    }
}