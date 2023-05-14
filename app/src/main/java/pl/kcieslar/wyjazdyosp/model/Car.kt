package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import pl.kcieslar.wyjazdyosp.utils.generateRandomUUID

@Parcelize
data class Car(
    override var key: String = generateRandomUUID(),
    override var name: String = "",
    override val type: ForcesDataType = ForcesDataType.CAR,
) : Parcelable, Forces