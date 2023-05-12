package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import pl.kcieslar.wyjazdyosp.ui.action.addOrEdit.stepThird.FiremanFunction

@Parcelize
data class Fireman(
    override var key: String = "",
    override val id: Int = -1,
    override val name: String = "",
) : Forces, Parcelable {
    @Exclude
    @IgnoredOnParcel
    var selectStatus: Int? = null
    @Exclude
    @IgnoredOnParcel
    var functions: MutableMap<Int, MutableList<FiremanFunction>> = mutableMapOf()
}