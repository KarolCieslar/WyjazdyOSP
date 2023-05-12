package pl.kcieslar.wyjazdyosp.data.firebaserepo

import pl.kcieslar.wyjazdyosp.model.Action
import pl.kcieslar.wyjazdyosp.model.Car

data class ActionResponse(
    var list: List<Action>? = null,
    var exception: Exception? = null
)