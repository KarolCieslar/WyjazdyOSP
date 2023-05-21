package pl.kcieslar.wyjazdyosp.model.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "fireman")
data class RoomFireman(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
) : Parcelable