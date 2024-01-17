package com.colddelight.madalart

import com.colddelight.model.Manda
import com.colddelight.model.Todo

sealed interface MandaUiState {
    data object Loading : MandaUiState

    data class Error(val msg: String) : MandaUiState

    data object NewUser : MandaUiState

    data class Success(
        val totCnt: Int,
        val mandaList: List<Manda>
    ) : MandaUiState

}