package pl.kcieslar.wyjazdyosp.data.firebaserepo

data class FirebaseCallResponse (
     var isSuccess: Boolean = false,
     var exception: Exception? = null
)