package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize
import pl.kcieslar.wyjazdyosp.ui.forces.ForcesDataType

@Keep
@Parcelize
data class Equipment(
    override var key: String = "",
    override var name: String = "",
    override val type: ForcesDataType = ForcesDataType.EQUIPMENT,
) : Parcelable, Forces {
    @Exclude
    fun convertToHashMap() : HashMap<String, Any?> {
        return hashMapOf(
            "name" to name,
        )
    }
}