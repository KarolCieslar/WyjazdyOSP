package pl.kcieslar.wyjazdyosp.data.response

import pl.kcieslar.wyjazdyosp.model.Group

data class StatusResponse(
    var list: List<Group>? = null,
    var exception: Exception? = null
)