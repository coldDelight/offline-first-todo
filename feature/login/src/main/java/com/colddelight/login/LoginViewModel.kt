package com.colddelight.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.repository.MandaRepository
import com.colddelight.data.repository.TodoRepository
import com.colddelight.datastore.datasource.UserPreferencesDataSource
import com.colddelight.network.SupabaseClient.client
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.Google
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userDataSource: UserPreferencesDataSource,
    private val todoRepository: TodoRepository,
    private val mandaRepository: MandaRepository,
) : ViewModel() {

//    init {
//        viewModelScope.launch {
//            bookDataSource.getBook()
//        }
//    }


    suspend fun loginG() {
        client.auth.signInWith(Google)
    }

    fun checkLoginStatus(result: NativeSignInResult) {
        when (result) {
            is NativeSignInResult.Success -> {
                todoRepository.init()
                mandaRepository.init()
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

}