package com.colddelight.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.colddelight.network.SupabaseClient.client
import io.github.jan.supabase.compose.auth.composable.rememberSignInWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.Google

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val supaaseLogin = client.composeAuth.rememberSignInWithGoogle(
        onResult = { result -> loginViewModel.checkLoginStatus(result) },
        fallback = {
            Log.e("TAG", "LoginScreen:이곳이다")
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {
//            Button(onClick = { loginViewModel.loginG() }) {
//                Text("로그인 버튼 ")
//            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { supaaseLogin.startFlow() }) {
                Text("로그인 버튼 ")
            }
        }
    }
}
