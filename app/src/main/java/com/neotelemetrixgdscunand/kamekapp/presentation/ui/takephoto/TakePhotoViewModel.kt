package com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.domain.common.StringRes
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.domain.presentation.CaptureImageFileHandler
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.util.ImageCompressor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TakePhotoViewModel @Inject constructor(
    private val imageCompressor: ImageCompressor,
    private val captureImageFileHandler: CaptureImageFileHandler
) : ViewModel() {

    private val _isConfirmationDialogShown = MutableStateFlow(false)
    private val _isUsingBackCamera = MutableStateFlow(true)
    private val _isCameraOpen = MutableStateFlow(true)
    private val _canUserInteractWithDialog = MutableStateFlow(true)

    private var capturedPhotoImagePath: String? = null

    val uiState = combine(
        _isConfirmationDialogShown,
        _isUsingBackCamera,
        _isCameraOpen,
        _canUserInteractWithDialog
    ) { isConfirmationDialogShown, isUsingBackCamera, isCameraOpen, canUserInteractWithDialog ->
        TakePhotoUIState(
            isConfirmationDialogShown = isConfirmationDialogShown,
            isUsingBackCamera = isUsingBackCamera,
            isCameraOpen = isCameraOpen,
            canUserInteractWithDialog = canUserInteractWithDialog,
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        TakePhotoUIState()
    )

    private val _uiEvent = Channel<TakePhotoUIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var submitJob: Job? = null

    fun onAction(action: TakePhotoUIAction) {
        when (action) {
            is TakePhotoUIAction.OnCaptureImageError -> {
                viewModelScope.launch {
                    val message = StringRes.Dynamic(action.error.message.toString())
                    _uiEvent.send(
                        TakePhotoUIEvent.ToastMessageEvent(message)
                    )
                }
            }

            is TakePhotoUIAction.OnCaptureImageSuccess -> {
                action.apply {
                    capturedPhotoImagePath = imageUriPath
                    if (imageUriPath != null) {
                        _isConfirmationDialogShown.value = true
                        _isCameraOpen.value = false
                    }
                }
            }

            TakePhotoUIAction.OnConfirmationDialogDismissed -> {
                action.apply {
                    capturedPhotoImagePath?.let { captureImageFileHandler.deleteImageFile(it) }
                    capturedPhotoImagePath = null
                    _isConfirmationDialogShown.value = false
                    _isCameraOpen.value = true
                }
            }

            is TakePhotoUIAction.OnConfirmationDialogSubmitted -> {
                if (action.submittedSessionName.isBlank()) {
                    val message = StringRes.Static(R.string.nama_yang_dimasukkan_tidak_valid)
                    viewModelScope.launch {
                        _uiEvent.send(
                            TakePhotoUIEvent.ToastMessageEvent(message)
                        )
                    }
                } else {
                    if (submitJob?.isActive == true) return

                    submitJob = viewModelScope.launch {
                        _canUserInteractWithDialog.value = false

                        delay(3000L)
                        val capturePhotoImagePath = capturedPhotoImagePath ?: return@launch
                        val compressedImage = imageCompressor.compressImage(capturePhotoImagePath)
                        val imagePath =
                            captureImageFileHandler.saveImage(
                                capturePhotoImagePath,
                                compressedImage,
                                action.submittedSessionName
                            )

                        if (imagePath == null) {
                            capturedPhotoImagePath?.let { captureImageFileHandler.deleteImageFile(it) }
                            capturedPhotoImagePath = null
                            _isConfirmationDialogShown.value = false
                            _isCameraOpen.value = true
                        } else {
                            _isConfirmationDialogShown.value = false
                            _uiEvent.send(
                                TakePhotoUIEvent.NavigateToResult(
                                    sessionName = action.submittedSessionName,
                                    imagePath = imagePath
                                )
                            )
                        }
                    }.also {
                        it.invokeOnCompletion {
                            _canUserInteractWithDialog.value = true
                            submitJob = null
                        }
                    }
                }
            }

            is TakePhotoUIAction.OnPickImageFromGalleryResult -> {
                action.apply {
                    capturedPhotoImagePath = imageUriPath
                    if (imageUriPath != null) {
                        _isConfirmationDialogShown.value = true
                        _isCameraOpen.value = false
                    }
                }
            }

            is TakePhotoUIAction.OnToggleIsUsingBackCamera -> {
                _isUsingBackCamera.value = !_isUsingBackCamera.value
            }
        }
    }

}