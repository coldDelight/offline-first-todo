package com.colddelight.daily

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DailyScreen(
    dailyViewModel: DailyViewModel = hiltViewModel(),
) {

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
            Button(onClick = { dailyViewModel.updateToken("sss") }) {
                Text("임시 토큰 추가 ")

            }

            Button(onClick = { dailyViewModel.delToken() }) {
                Text("임시 토큰 삭제 ")

            }
        }
    }
}