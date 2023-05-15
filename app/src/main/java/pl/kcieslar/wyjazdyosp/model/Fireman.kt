package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import pl.kcieslar.wyjazdyosp.ui.action.addOrEdit.stepThird.FiremanFunction
import pl.kcieslar.wyjazdyosp.ui.forces.ForcesDataType

@Parcelize
data class Fireman(
    override var key: String = "",
    override var name: String = "",
    override val type: ForcesDataType = ForcesDataType.FIREMAN,
) : Forces, Parcelable {
    @Exclude
    @IgnoredOnParcel
    var selectedCar: String? = null
    @Exclude
    @IgnoredOnParcel
    var functions: MutableMap<String, MutableList<FiremanFunction>> = mutableMapOf()
}