package pl.globoox.ospreportv3.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "car_in_actions_table")
data class CarInAction(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val car: Car,
    val firemans: List<Fireman>
) : Parcelable