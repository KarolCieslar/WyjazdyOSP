package pl.globoox.ospreportv3.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "equipments")
data class Equipment(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    override val name: String
) : Parcelable, Forces