package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarInAction(
    val car: Car = Car(),
    val firemans: List<Fireman> = emptyList()
) : Parcelable