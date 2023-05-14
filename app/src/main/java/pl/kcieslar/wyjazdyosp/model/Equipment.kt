package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import pl.kcieslar.wyjazdyosp.ui.forces.ForcesDataType
import pl.kcieslar.wyjazdyosp.utils.generateRandomUUID

@Parcelize
data class Equipment(
    override var key: String = generateRandomUUID(),
    override var name: String = "",
    override val type: ForcesDataType = ForcesDataType.EQUIPMENT,
) : Parcelable, Forces