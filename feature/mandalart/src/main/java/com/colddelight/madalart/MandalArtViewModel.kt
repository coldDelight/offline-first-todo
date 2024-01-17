package com.colddelight.madalart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.repository.MandaRepository
import com.colddelight.data.repository.TodoRepository
import com.colddelight.model.Manda
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MandalArtViewModel @Inject constructor(
    private val repository: MandaRepository,

    ) : ViewModel() {

    private val isNewFlow = repository.isNewUser
    private val mandaListFlow = repository.getAllManda()

    val mandaUiState: StateFlow<MandaUiState> =
        isNewFlow.combine(mandaListFlow) { isNew, mandaList ->
            if (isNew) {
                MandaUiState.NewUser
            } else {
                MandaUiState.Success(mandaList = mandaList, totCnt = mandaList.sumOf { it.cnt })
            }
        }.catch {
            MandaUiState.Error(it.message ?: "Error")

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MandaUiState.Loading
        )


    fun initManda() {
        viewModelScope.launch {
            repository.initManda()
        }
    }

    fun deleteAllManda() {
        viewModelScope.launch {
            repository.deleteAllManda()
        }
    }

    fun updateManda(manda: Manda) {
        viewModelScope.launch {
            repository.updateManda(manda.id, manda.cnt + 1)
        }
    }
}