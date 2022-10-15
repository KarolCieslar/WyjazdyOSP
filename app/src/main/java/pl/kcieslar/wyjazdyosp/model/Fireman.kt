package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import pl.kcieslar.wyjazdyosp.ui.action.addOrEdit.stepThird.FiremanFunction

@Parcelize
@Entity(tableName = "fireman")
data class Fireman(
    @PrimaryKey(autoGenerate = true)
    override val id: Int,
    override val name: String
) : Parcelable, Forces {
    @IgnoredOnParcel
    @Ignore var selectStatus: Int? = null
    @IgnoredOnParcel
    @Ignore var functions: MutableMap<Int, MutableList<FiremanFunction>> = mutableMapOf()
}