package com.example.data.model

data class AuthUser(
    val uid: String,
    val email: String,
    val displayName: String? = null,
    val photoUrl: String? = null,
    val isEmailVerified: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Authenticated(val user: AuthUser) : AuthUiState()
    object Unauthenticated : AuthUiState()
    data class Error(val message: String) : AuthUiState()
    data class PasswordResetSent(val email: String) : AuthUiState()
}

sealed class SyncUiState {
    object Idle : SyncUiState()
    object Syncing : SyncUiState()
    data class Synced(val timestamp: Long = System.currentTimeMillis(), val itemsSynced: Int = 0) : SyncUiState()
    data class Error(val message: String) : SyncUiState()
}
