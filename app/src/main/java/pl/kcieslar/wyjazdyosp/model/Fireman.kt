package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import pl.kcieslar.wyjazdyosp.ui.action.addOrEdit.stepThird.FiremanFunction
import pl.kcieslar.wyjazdyosp.ui.forces.ForcesDataType
import pl.kcieslar.wyjazdyosp.utils.generateRandomUUID

@Parcelize
data class Fireman(
    override var key: String = generateRandomUUID(),
    override var name: String = "",
    override val type: ForcesDataType = ForcesDataType.FIREMAN,
) : Forces, Parcelable {
    @Exclude
    @IgnoredOnParcel
    var selectStatus: String? = null
    @Exclude
    @IgnoredOnParcel
    var functions: MutableMap<String, MutableList<FiremanFunction>> = mutableMapOf()
}