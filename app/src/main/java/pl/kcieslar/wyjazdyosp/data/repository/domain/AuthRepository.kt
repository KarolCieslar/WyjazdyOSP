package pl.kcieslar.wyjazdyosp.data.repository.domain

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {

    suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser?

    suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser?

    fun signOut(): FirebaseUser?

    fun getUser(): FirebaseUser?

    fun getUserUid(): String

    suspend fun sendPasswordReset(email: String): Boolean
}