package com.example.data.remote

import android.util.Log
import com.example.data.model.AuthUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseAuthService {

    private val auth: FirebaseAuth? by lazy {
        try {
            FirebaseAuth.getInstance()
        } catch (e: Exception) {
            Log.w("FirebaseAuthService", "Firebase Auth initialization failed or not available: ${e.message}")
            null
        }
    }

    private val _currentUserFlow = MutableStateFlow<AuthUser?>(null)
    val currentUserFlow: StateFlow<AuthUser?> = _currentUserFlow.asStateFlow()

    init {
        try {
            auth?.addAuthStateListener { firebaseAuth ->
                val fbUser = firebaseAuth.currentUser
                _currentUserFlow.value = fbUser?.toAuthUser()
            }
        } catch (e: Exception) {
            Log.w("FirebaseAuthService", "Could not attach AuthStateListener: ${e.message}")
        }
    }

    val currentUserId: String?
        get() = _currentUserFlow.value?.uid ?: auth?.currentUser?.uid

    val isAuthenticated: Boolean
        get() = currentUserId != null

    fun getCurrentUser(): AuthUser? {
        return _currentUserFlow.value ?: auth?.currentUser?.toAuthUser()
    }

    suspend fun registerWithEmail(email: String, pass: String, displayName: String): AuthUser {
        val trimmedEmail = email.trim()
        val trimmedPass = pass.trim()
        val trimmedName = displayName.trim()

        if (trimmedEmail.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            throw IllegalArgumentException("Please provide a valid email address.")
        }
        if (trimmedPass.length < 6) {
            throw IllegalArgumentException("Password must be at least 6 characters.")
        }

        val firebaseAuth = auth ?: throw IllegalStateException("Firebase Authentication service is unavailable.")

        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(trimmedEmail, trimmedPass).await()
            val user = result.user ?: throw IllegalStateException("Registration succeeded but user profile was null.")

            if (trimmedName.isNotBlank()) {
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(trimmedName)
                    .build()
                user.updateProfile(profileUpdates).await()
            }

            val authUser = AuthUser(
                uid = user.uid,
                email = user.email ?: trimmedEmail,
                displayName = trimmedName.ifBlank { user.displayName }
            )
            _currentUserFlow.value = authUser
            return authUser
        } catch (e: FirebaseAuthWeakPasswordException) {
            throw IllegalArgumentException("Password is too weak. Please use at least 6 characters including numbers and letters.")
        } catch (e: FirebaseAuthUserCollisionException) {
            throw IllegalArgumentException("An account already exists with this email address.")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            throw IllegalArgumentException("The email address format is invalid.")
        } catch (e: Exception) {
            throw Exception(e.localizedMessage ?: "Registration failed. Please verify your connection and try again.")
        }
    }

    suspend fun loginWithEmail(email: String, pass: String): AuthUser {
        val trimmedEmail = email.trim()
        val trimmedPass = pass.trim()

        if (trimmedEmail.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            throw IllegalArgumentException("Please enter a valid email address.")
        }
        if (trimmedPass.isBlank()) {
            throw IllegalArgumentException("Please enter your password.")
        }

        val firebaseAuth = auth ?: throw IllegalStateException("Firebase Authentication service is unavailable.")

        try {
            val result = firebaseAuth.signInWithEmailAndPassword(trimmedEmail, trimmedPass).await()
            val user = result.user ?: throw IllegalStateException("Sign in returned no user.")
            val authUser = user.toAuthUser()
            _currentUserFlow.value = authUser
            return authUser
        } catch (e: FirebaseAuthInvalidUserException) {
            throw IllegalArgumentException("No account found with this email. Please check your email or register.")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            throw IllegalArgumentException("Incorrect password or email credentials.")
        } catch (e: Exception) {
            throw Exception(e.localizedMessage ?: "Sign in failed. Please check your credentials.")
        }
    }

    suspend fun sendPasswordReset(email: String) {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            throw IllegalArgumentException("Please enter a valid email address to reset password.")
        }

        val firebaseAuth = auth ?: throw IllegalStateException("Firebase Authentication service is unavailable.")

        try {
            firebaseAuth.sendPasswordResetEmail(trimmedEmail).await()
        } catch (e: FirebaseAuthInvalidUserException) {
            throw IllegalArgumentException("No account registered with this email.")
        } catch (e: Exception) {
            throw Exception(e.localizedMessage ?: "Failed to send password reset email.")
        }
    }

    fun signOut() {
        try {
            auth?.signOut()
        } catch (e: Exception) {
            Log.w("FirebaseAuthService", "Error during sign out: ${e.message}")
        }
        _currentUserFlow.value = null
    }

    private fun FirebaseUser.toAuthUser(): AuthUser {
        return AuthUser(
            uid = this.uid,
            email = this.email ?: "",
            displayName = this.displayName,
            photoUrl = this.photoUrl?.toString(),
            isEmailVerified = this.isEmailVerified
        )
    }
}
