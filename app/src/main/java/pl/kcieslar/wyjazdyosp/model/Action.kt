package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.time.format.DateTimeFormatter
import java.util.*

@Parcelize
data class Action(
    @Exclude @set:Exclude
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
    fun getFormattedOutTime() : String {
        val dateFormatterHelper: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.ROOT)
        return outTime.format(dateFormatterHelper)
    }
    @Exclude
    fun getFormattedInTime() : String {
        val dateFormatterHelper: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.ROOT)
        return inTime.format(dateFormatterHelper)
    }
}