package com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.neotelemetrixgdscunand.kamekapp.MainActivity
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.component.BottomBarTakePhoto
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.component.CameraPreview
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.component.InputSessionNameDialog
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.component.TopBarTakePhoto
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.util.collectChannelWhenStarted
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.util.getValue

@Composable
fun TakePhotoScreen(
    modifier: Modifier = Modifier,
    viewModel: TakePhotoViewModel = hiltViewModel(),
    isCameraPermissionGranted: Boolean?,
    showSnackBar: (String) -> Unit = {},
    navigateUp: () -> Unit = {},
    navigateToResult: (String, String) -> Unit = { _, _ -> }
) {
    val context = LocalContext.current

    LaunchedEffect(true) {
        if (isCameraPermissionGranted == null && context is MainActivity) {
            context.checkCameraPermission()
        }
    }

    val permissionDeniedMessage = stringResource(R.string.fitur_tidak_bisa_diakses)
    LaunchedEffect(isCameraPermissionGranted) {
        if (isCameraPermissionGranted == false) {
            showSnackBar(
                permissionDeniedMessage
            )
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(true) {
        lifecycleOwner.collectChannelWhenStarted(
            viewModel.uiEvent
        ){
            when(it){
                is TakePhotoUIEvent.ToastMessageEvent -> {
                    showSnackBar(it.message.getValue(context))
                }
                is TakePhotoUIEvent.NavigateToResult -> {
                    navigateToResult(
                        it.sessionName,
                        it.imagePath
                    )
                }
            }
        }
    }

    val uiState by viewModel.uiState.collectAsState()

    TakePhotoContent(
        modifier = modifier,
        isCameraPermissionGranted = isCameraPermissionGranted,
        navigateUp = navigateUp,
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
fun TakePhotoContent(
    modifier: Modifier = Modifier,
    isCameraPermissionGranted: Boolean? = false,
    navigateUp:() -> Unit = { },
    uiState: TakePhotoUIState = TakePhotoUIState(),
    onAction:(TakePhotoUIAction) -> Unit = { }
) {
    val context = LocalContext.current

    var sessionName by rememberSaveable { mutableStateOf("") }
    val imageCapture = remember {
        ImageCapture.Builder()
            .build()
    }

    val cameraPreviewHeightRatio = 0.8f
    val topBarHeightRatio = 0.089f
    val bottomBarStartMarginRatio = 0.11864f
    val bottomBarEndMarginRatio = 0.09864f
    if (isCameraPermissionGranted == true) {

        val photoPickerLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) {
            onAction(
                TakePhotoUIAction.OnPickImageFromGalleryResult(
                    it.toString()
                )
            )
        }

        Column(
            modifier.fillMaxSize()
        ) {

            TopBarTakePhoto(
                topBarHeightRatio = topBarHeightRatio,
                switchCameraLens = {
                    onAction(
                        TakePhotoUIAction.OnToggleIsUsingBackCamera
                    )
                },
                cancelSession = navigateUp
            )

            CameraPreview(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(cameraPreviewHeightRatio),
                isUsingBackCamera = uiState.isUsingBackCamera,
                imageCapture,
                uiState.isCameraOpen
            )

            BottomBarTakePhoto(
                onCaptureImage = {
                    captureImage(
                        imageCapture,
                        context,
                        onSuccess = { uri ->
                            onAction(
                                TakePhotoUIAction.OnCaptureImageSuccess(
                                    uri.toString()
                                )
                            )
                        },
                        onError = { error ->
                            onAction(
                                TakePhotoUIAction.OnCaptureImageError(
                                    error
                                )
                            )
                        }
                    )
                },
                onGetImageFromGallery = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
                bottomBarStartMarginRatio = bottomBarStartMarginRatio,
                bottomBarEndMarginRatio = bottomBarEndMarginRatio
            )

        }

        InputSessionNameDialog(
            isShowDialog = uiState.isConfirmationDialogShown,
            name = sessionName,
            canUserInteract = uiState.canUserInteractWithDialog,
            onValueNameChange = {
                if (it.length < 50) {
                    sessionName = it
                }
            },
            onDismiss = {
                sessionName = ""
                onAction(
                    TakePhotoUIAction.OnConfirmationDialogDismissed
                )
            },
            onSubmit = {
                onAction(
                    TakePhotoUIAction.OnConfirmationDialogSubmitted(
                        sessionName
                    )
                )
            }
        )
    }
}


@Preview
@Composable
private fun TakePhotoScreenPreview() {
    KamekAppTheme {
        TakePhotoScreen(
            isCameraPermissionGranted = true
        )
    }
}