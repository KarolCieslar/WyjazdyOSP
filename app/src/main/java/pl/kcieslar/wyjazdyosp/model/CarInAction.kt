package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarInAction(
    val car: Car,
    val firemans: List<Fireman>
) : Parcelable