package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cars")
data class Car(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    override val name: String,
) : Parcelable, Forces