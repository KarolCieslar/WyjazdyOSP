package pl.globoox.ospreportv3.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarInAction(
    val car: Car,
    val firemans: List<Fireman>
) : Parcelable