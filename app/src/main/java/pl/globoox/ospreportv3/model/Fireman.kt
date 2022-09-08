package pl.globoox.ospreportv3.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import pl.globoox.ospreportv3.ui.action.add.stepThird.FiremanFunction

@Parcelize
@Entity(tableName = "fireman")
data class Fireman(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
) : Parcelable {
    @Ignore var selectStatus: Int? = null
    @Ignore var functions: MutableList<FiremanFunction>? = mutableListOf()
}