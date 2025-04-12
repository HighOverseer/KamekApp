package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult

import android.icu.util.Calendar
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.dicoding.asclepius.domain.common.StringRes
import com.neotelemetrixgdscunand.kamekapp.domain.data.Repository
import com.neotelemetrixgdscunand.kamekapp.domain.model.CacaoDisease
import com.neotelemetrixgdscunand.kamekapp.domain.model.DetectedCacao
import com.neotelemetrixgdscunand.kamekapp.domain.model.DiagnosisSession
import com.neotelemetrixgdscunand.kamekapp.domain.presentation.ImageDetectorHelper
import com.neotelemetrixgdscunand.kamekapp.domain.presentation.ImageDetectorResult
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.Navigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class DiagnosisResultViewModel @Inject constructor(
    private val repository: Repository,
    private val imageClassifierHelper: ImageDetectorHelper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(DiagnosisResultUIState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<DiagnosisResultUIEvent>()
    val event = _event.receiveAsFlow()

    private var detectImageJob: Job? = null

    private val extras = savedStateHandle.toRoute<Navigation.Main.DiagnosisResult>()

    init {
        listenToImageDetectorResult()
        val isFromNewSession =
            extras.newSessionName != null && extras.newUnsavedSessionImagePath != null

        if (isFromNewSession) {
            detectImage(extras.newUnsavedSessionImagePath as String)
        } else {
            extras.sessionId?.let { sessionId ->
                val selectedDiagnosisSession = repository.getDiagnosisSession(sessionId)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        diagnosisSession = selectedDiagnosisSession,
                        imagePreviewPath = selectedDiagnosisSession.imageUrlOrPath
                    )
                }
            }

        }
    }

    private fun listenToImageDetectorResult() {
        viewModelScope.launch {
            imageClassifierHelper.result.collect { result ->
                when (result) {
                    ImageDetectorResult.NoObjectDetected -> {
                        viewModelScope.launch {
                            _event.send(
                                DiagnosisResultUIEvent.OnInputImageInvalid
                            )
                        }
                    }

                    is ImageDetectorResult.Error -> {
                        viewModelScope.launch {
                            _event.send(
                                DiagnosisResultUIEvent.OnToastMessage(
                                    StringRes.Dynamic(
                                        result.exception.message.toString()
                                    )
                                )
                            )
                        }
                    }

                    is ImageDetectorResult.Success -> {
                        val extras = this@DiagnosisResultViewModel.extras
                        if (extras.newSessionName == null || extras.newUnsavedSessionImagePath == null) return@collect

                        val detectedCacaos = result.boundingBoxes.mapIndexed { index, item ->
                            if (detectImageJob?.isActive == false) return@collect

                            DetectedCacao(
                                cacaoNumber = index.plus(1).toShort(),
                                boundingBox = item,
                                disease = CacaoDisease.getDiseaseFromName(
                                    name = item.label
                                ) ?: return@collect
                            )
                        }

                        if (detectImageJob?.isActive == false) return@collect

                        val newSessionId = saveDiagnosisResult(
                            sessionName = extras.newSessionName,
                            imagePath = extras.newUnsavedSessionImagePath,
                            predictedPrice = 1680f,
                            detectedCacaos = detectedCacaos
                        )

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                diagnosisSession = repository.getDiagnosisSession(newSessionId)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun saveDiagnosisResult(
        sessionName: String,
        imagePath: String,
        predictedPrice: Float,
        detectedCacaos: List<DetectedCacao>
    ): Int {

        val formatDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val dateString = formatDate.format(
            Calendar.getInstance().time
        )
        val newDiagnosisSession = DiagnosisSession(
            id = Random.nextInt(0, 1000_000_000),
            title = sessionName,
            imageUrlOrPath = imagePath,
            date = dateString,
            predictedPrice = predictedPrice,
            detectedCacaos = detectedCacaos
        )

        repository.saveDiagnosis(
            newDiagnosisSession = newDiagnosisSession
        )
        return newDiagnosisSession.id
    }

    fun changeSelectedTab() {
        _uiState.update {
            it.copy(
                isDiagnosisTabSelected = !it.isDiagnosisTabSelected
            )
        }
    }

    private fun detectImage(imagePath: String) {
        if (detectImageJob != null) return

        _uiState.update { it.copy(isLoading = true, imagePreviewPath = imagePath) }
        detectImageJob = viewModelScope.launch {
            imageClassifierHelper.detect(imagePath)
        }.apply {
            invokeOnCompletion {
                _uiState.update { it.copy(isLoading = false) }
                detectImageJob = null
            }
        }
    }

    override fun onCleared() {
        imageClassifierHelper.clearResource()
        super.onCleared()
    }

}