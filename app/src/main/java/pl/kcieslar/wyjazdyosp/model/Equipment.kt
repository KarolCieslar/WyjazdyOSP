package pl.kcieslar.wyjazdyosp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Equipment(
    override var key: String = "",
    override val id: Int = -1,
    override val name: String = "",
) : Parcelable, Forces