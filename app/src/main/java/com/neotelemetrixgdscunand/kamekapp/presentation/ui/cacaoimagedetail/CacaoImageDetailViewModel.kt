package com.neotelemetrixgdscunand.kamekapp.presentation.ui.cacaoimagedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.neotelemetrixgdscunand.kamekapp.domain.data.Repository
import com.neotelemetrixgdscunand.kamekapp.domain.model.BoundingBox
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CacaoImageDetailViewModel @Inject constructor(
    val repository: Repository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val imagePath: String
    val boundingBox: BoundingBox

    init {
        val extras = savedStateHandle.toRoute<Navigation.Main.CacaoImageDetail>()
        val selectedDiagnosisSession = repository.getDiagnosisSession(extras.diagnosisSessionId)

        imagePath = selectedDiagnosisSession.imageUrlOrPath
        boundingBox = selectedDiagnosisSession
            .detectedCacaos
            .find { it.id == extras.detectedCacaoId }
            ?.boundingBox ?: throw Exception("Cacao matching given id is not found")

    }
}