package pl.globoox.ospreportv3.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import pl.globoox.ospreportv3.ui.action.addOrEdit.stepThird.FiremanFunction

data class Quarter(
    val name: String,
    val year: Int,
    val quarter: Int
)