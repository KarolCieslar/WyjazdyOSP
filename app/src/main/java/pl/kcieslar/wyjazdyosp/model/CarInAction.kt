package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class CarInAction(
    val car: Car = Car(),
    val firemans: List<Fireman> = emptyList()
) : Parcelable {
    @Exclude
    fun convertToHashMap() : HashMap<String, Any?> {
        return hashMapOf(
            "car" to car.convertToHashMap(),
            "firemans" to firemans.map { it.convertToHashMap() }
        )
    }
}