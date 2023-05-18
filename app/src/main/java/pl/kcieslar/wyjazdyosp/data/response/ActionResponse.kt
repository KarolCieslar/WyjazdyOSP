package pl.kcieslar.wyjazdyosp.data.response

import pl.kcieslar.wyjazdyosp.model.Action

data class ActionResponse(
    var list: List<Action>? = null,
    var exception: Exception? = null
)