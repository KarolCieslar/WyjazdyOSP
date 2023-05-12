package pl.kcieslar.wyjazdyosp.data.firebaserepo

import pl.kcieslar.wyjazdyosp.model.Car

data class CarResponse (
    override var list: List<Car>? = null,
    override var exception: Exception? = null
) : FirebaseForcesResponse