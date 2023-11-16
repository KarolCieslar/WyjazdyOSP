package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.database.Exclude
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import pl.kcieslar.wyjazdyosp.ui.action.addOrEdit.stepThird.FiremanFunction
import pl.kcieslar.wyjazdyosp.ui.forces.ForcesDataType

@Keep
@Parcelize
data class Fireman(
    override var key: String = "",
    override var name: String = "",
    override val type: ForcesDataType = ForcesDataType.FIREMAN,
) : Forces, Parcelable {
    @Exclude
    @IgnoredOnParcel
    var ownCarFunction: Boolean = false

    @Exclude
    @IgnoredOnParcel
    var driverFunction: Boolean = false

    @Exclude
    @IgnoredOnParcel
    var commanderFunction: Boolean = false

    @Exclude
    @IgnoredOnParcel
    var selectedCar: String? = null

    @Exclude
    fun changeFiremanFunction(function: FiremanFunction, value: Boolean? = null) {
        when (function) {
            FiremanFunction.OWNCAR -> ownCarFunction = value ?: !ownCarFunction
            FiremanFunction.DRIVER -> driverFunction = value ?: !driverFunction
            FiremanFunction.COMMANDER -> commanderFunction = value ?: !commanderFunction
        }
    }

    @Exclude
    fun getFiremanFunction(function: FiremanFunction) : Boolean {
        return when (function) {
            FiremanFunction.OWNCAR -> ownCarFunction
            FiremanFunction.DRIVER -> driverFunction
            FiremanFunction.COMMANDER -> commanderFunction
        }
    }

    @Exclude
    fun convertToHashMap(): HashMap<String, Any?> {
        return hashMapOf(
            "selectedCar" to selectedCar,
            "key" to key,
            "name" to name,
            "ownCarFunction" to ownCarFunction,
            "driverFunction" to driverFunction,
            "commanderFunction" to commanderFunction
        )
    }

}