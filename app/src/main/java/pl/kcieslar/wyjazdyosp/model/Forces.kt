package pl.kcieslar.wyjazdyosp.model

import androidx.annotation.Keep
import pl.kcieslar.wyjazdyosp.ui.forces.ForcesDataType

@Keep
interface Forces {
    var key: String
    var name: String
    val type: ForcesDataType
}