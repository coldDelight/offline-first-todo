package com.colddelight.madalart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MandalArtScreen(
    mandalArtViewModel: MandalArtViewModel = hiltViewModel(),
){

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ){ padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize()
        ) {
            Text(
                text = "MandalArt",
            )
        }
    }
}