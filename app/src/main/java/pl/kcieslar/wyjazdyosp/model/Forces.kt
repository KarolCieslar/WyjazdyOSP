package pl.kcieslar.wyjazdyosp.model

import pl.kcieslar.wyjazdyosp.ui.forces.ForcesDataType

interface Forces {
    var key: String
    var name: String
    val type: ForcesDataType
}