package pl.kcieslar.wyjazdyosp.data.firebaserepo

import pl.kcieslar.wyjazdyosp.model.Fireman

data class FiremanResponse (
    override var list: List<Fireman>? = null,
    override var exception: Exception? = null
) : FirebaseForcesResponse