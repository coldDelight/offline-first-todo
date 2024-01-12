package com.colddelight.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.datastore.datasource.UserPreferencesDataSource
import com.colddelight.network.SupabaseClient
import com.colddelight.network.SupabaseClient.client
import com.colddelight.network.datasource.BookDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.Google
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userDataSource: UserPreferencesDataSource,
    private val bookDataSource: BookDataSource,

    ) : ViewModel() {

    init {
        viewModelScope.launch {
            bookDataSource.getBook()
        }
    }

    suspend fun loginG() {
        client.auth.signInWith(Google)
    }

    fun checkLoginStatus(result: NativeSignInResult) {
        when (result) {
            is NativeSignInResult.Success -> {
                updateToken(client.auth.currentAccessTokenOrNull() ?: "")
            }
            is NativeSignInResult.ClosedByUser -> {
            }
            is NativeSignInResult.Error -> {
                viewModelScope.launch {
                    loginG()
                }
            }
            is NativeSignInResult.NetworkError -> {

            }
        }
    }

    private fun updateToken(token: String) {
        viewModelScope.launch {
            userDataSource.saveToken(token)
        }
    }

    fun delToken() {
        viewModelScope.launch {
            userDataSource.delToken()
        }
    }
}