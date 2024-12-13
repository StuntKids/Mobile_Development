package com.example.stuntkids.data.repository

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FirebaseRepository(
    private val context: Context
) {
    private val firebaseApp = FirebaseApp.initializeApp(context)
    val firebaseAuth: FirebaseAuth? = firebaseApp?.let { FirebaseAuth.getInstance(it) }

    suspend fun signUp(name: String, email: String, password: String) {
        return withContext(Dispatchers.IO) {
            firebaseAuth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        val changeRequest = UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()
                        user?.updateProfile(changeRequest)
                    }
                }
                ?.addOnFailureListener {
                    it.printStackTrace()
                    throw it
                }
        }
    }

    suspend fun login(email: String, password: String) {
        return withContext(Dispatchers.IO) {
            firebaseAuth?.signInWithEmailAndPassword(email, password)
                ?.addOnFailureListener {
                    it.printStackTrace()
                    throw it
                }
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth?.currentUser
    }

    fun logout() {
        firebaseAuth?.signOut()
    }
}