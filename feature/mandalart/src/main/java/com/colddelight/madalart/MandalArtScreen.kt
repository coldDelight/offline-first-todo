package com.colddelight.madalart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.model.Manda

@Composable
fun MandalArtScreen(
    mandalArtViewModel: MandalArtViewModel = hiltViewModel(),
) {

    val mandaUiState by mandalArtViewModel.mandaUiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            MandaContentWithState(
                uiState = mandaUiState,
                onInit = { mandalArtViewModel.initManda() },
                onDelete = { mandalArtViewModel.deleteAllManda() },
                onUpdate = { manda -> mandalArtViewModel.updateManda(manda) },
            )
        }
    }
}

@Composable
private fun MandaContentWithState(
    uiState: MandaUiState,
    onInit: () -> Unit,
    onDelete: () -> Unit,
    onUpdate: (manda: Manda) -> Unit
) {
    when (uiState) {
        is MandaUiState.Loading -> {}
        is MandaUiState.Error -> Text(text = uiState.msg)
        is MandaUiState.NewUser -> OnBoardingContent(onInit)
        is MandaUiState.Success -> MandaContent(
            uiState.totCnt,
            uiState.mandaList,
            onDelete = onDelete,
            onUpdate = onUpdate,
        )
    }
}

@Composable
private fun MandaContent(
    totCnt: Int,
    mandaList: List<Manda>,
    onDelete: () -> Unit,
    onUpdate: (manda: Manda) -> Unit
) {

    Column {

        Text(text = "총 $totCnt")

        Button(onClick = { onDelete() }) {
            Text(text = "초기화")

        }
    }

}

@Composable
private fun OnBoardingContent(
    onInit: () -> Unit
) {
    Column {

        Button(
            onClick = { onInit() }, modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1F)
        ) {
            Text(text = "생성하기")
        }
    }


}