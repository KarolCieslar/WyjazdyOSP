package pl.kcieslar.wyjazdyosp.data.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import pl.kcieslar.wyjazdyosp.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {
    override suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser? {
        Firebase.auth.createUserWithEmailAndPassword(email, password).await()
        return Firebase.auth.currentUser
    }

    override suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser? {
        Firebase.auth.signInWithEmailAndPassword(email, password).await()
        return Firebase.auth.currentUser
    }

    override fun signOut(): FirebaseUser? {
        Firebase.auth.signOut()
        return Firebase.auth.currentUser
    }

    override fun getUser(): FirebaseUser? {
        return Firebase.auth.currentUser
    }

    override suspend fun sendPasswordReset(email: String) : Boolean {
        Firebase.auth.sendPasswordResetEmail(email).await()
        return true
    }
}