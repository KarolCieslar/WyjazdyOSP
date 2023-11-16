package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize
import pl.kcieslar.wyjazdyosp.ui.forces.ForcesDataType

@Keep
@Parcelize
data class Car(
    override var key: String = "",
    override var name: String = "",
    override val type: ForcesDataType = ForcesDataType.CAR,
) : Parcelable, Forces {
    @Exclude
    fun convertToHashMap() : HashMap<String, Any?> {
        return hashMapOf(
            "name" to name,
            "key" to key
        )
    }
}