package pl.kcieslar.wyjazdyosp.data.firebaserepo

import pl.kcieslar.wyjazdyosp.model.Equipment

data class EquipmentResponse (
    override var list: List<Equipment>? = null,
    override var exception: Exception? = null
) : FirebaseForcesResponse