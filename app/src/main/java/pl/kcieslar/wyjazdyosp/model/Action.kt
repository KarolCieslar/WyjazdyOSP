package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.format.DateTimeFormatter
import java.util.*

@Parcelize
@Entity(tableName = "actions")
data class Action(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val outTime: String,
    val inTime: String,
    val location: String,
    val number: String,
    val description: String? = null,
    val carsInAction: List<CarInAction>,
    val equipment: List<Equipment>
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