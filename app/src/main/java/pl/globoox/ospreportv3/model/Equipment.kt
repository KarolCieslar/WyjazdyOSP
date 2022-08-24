package pl.globoox.ospreportv3.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "equipments_table")
data class Equipment(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
) : Parcelable