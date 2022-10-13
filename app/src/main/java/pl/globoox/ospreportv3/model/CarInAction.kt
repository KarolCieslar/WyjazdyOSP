package pl.globoox.ospreportv3.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarInAction(
    val car: Car,
    val firemans: List<Fireman>
) : Parcelable