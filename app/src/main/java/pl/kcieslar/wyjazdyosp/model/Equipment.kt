package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import pl.kcieslar.wyjazdyosp.ui.forces.ForcesDataType

@Parcelize
data class Equipment(
    override var key: String = "",
    override var name: String = "",
    override val type: ForcesDataType = ForcesDataType.EQUIPMENT,
) : Parcelable, Forces