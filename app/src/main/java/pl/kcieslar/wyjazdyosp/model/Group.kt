package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Group(
    val code: String = "",
    val name: String = "",
    val description: String = "",
    val adminKey: String = "",
    val users: Map<String, User> = emptyMap()
) : Parcelable {
    fun getUserList(): List<User> {
        return this.users.map { it.value }
    }

    fun getUsersWithReadyStatus(): List<User> {
        return this.users.map { it.value }.filter { it.status ==  UserStatus.READY}
    }
}