package pl.kcieslar.wyjazdyosp.data.response

data class FirebaseCallResponse (
     var isSuccess: Boolean = false,
     var exception: Exception? = null
)