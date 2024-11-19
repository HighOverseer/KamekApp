package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neotelemetrixgdscunand.kamekapp.domain.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
):ViewModel() {

    val diagnosisHistory = repository.getAllDiagnosisHistories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


}