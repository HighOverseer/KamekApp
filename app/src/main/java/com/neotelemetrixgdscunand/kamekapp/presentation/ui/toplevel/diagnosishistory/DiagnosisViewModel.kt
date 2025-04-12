package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.diagnosishistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neotelemetrixgdscunand.kamekapp.domain.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DiagnosisViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val diagnosisHistoryPreview = repository.getAllSavedDiagnosisSessionPreviews()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    var job: Job? = null

    fun search(query: String) {
        job?.cancel()
        job = viewModelScope.launch {
            withContext(Dispatchers.Default) {
                diagnosisHistoryPreview.value.filter {
                    it.title.contains("query", ignoreCase = true)
                }
            }
        }
    }
}