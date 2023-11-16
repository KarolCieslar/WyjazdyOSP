package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.database.PropertyName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class User(
    val name: String = "",
    val description: String = "",
    val status: UserStatus = UserStatus.BUSY,
) : Parcelable

enum class UserStatus(val value: String) {
    @PropertyName("READY")
    READY("READY"),

    @PropertyName("BUSY")
    BUSY("BUSY")
}
