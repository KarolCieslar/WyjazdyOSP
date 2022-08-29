package pl.globoox.ospreportv3.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import pl.globoox.ospreportv3.ui.action.add.AddAction

@Parcelize
@Entity(tableName = "cars_table")
data class Car(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String?,
) : Parcelable