package com.colddelight.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
) {

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            Button(onClick = { loginViewModel.updateToken("sss") }) {
                Text("임시 토큰 추가 ")

            }

            Button(onClick = { loginViewModel.delToken() }) {
                Text("임시 토큰 삭제 ")

            }
            Text(
                text = "Login",
            )
        }
    }
}