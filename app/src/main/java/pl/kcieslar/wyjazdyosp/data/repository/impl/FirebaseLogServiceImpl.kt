package pl.kcieslar.wyjazdyosp.data.repository.impl

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import pl.kcieslar.wyjazdyosp.data.repository.domain.FirebaseLogService
import timber.log.Timber
import javax.inject.Inject

class FirebaseLogServiceImpl @Inject constructor() : FirebaseLogService {
    override fun logNonFatalCrash(throwable: Throwable) =
        Firebase.crashlytics.recordException(throwable)

    override fun printStackTrace(throwable: Throwable) {
        Timber.e(throwable.printStackTrace().toString())
    }
}
