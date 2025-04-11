package com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
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
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.component.TextFieldConfirmationDialog
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.component.TopBarTakePhoto
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.util.CameraState
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.util.TakePhotoUIEvent
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.util.TextFieldConfirmationDialogState
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
        ) {
            when (it) {
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

    TakePhotoContent(
        modifier = modifier,
        isCameraPermissionGranted = isCameraPermissionGranted,
        navigateUp = navigateUp,
        cameraState = viewModel.cameraState,
        textFieldConfirmationDialogState = viewModel.textFieldConfirmationDialogState,
        handlePickImageFromGalleryResult = viewModel::handleOnPickImageGalleryResult
    )
}

@Composable
fun TakePhotoContent(
    modifier: Modifier = Modifier,
    isCameraPermissionGranted: Boolean? = false,
    navigateUp: () -> Unit = { },
    cameraState: CameraState = CameraState(rememberCoroutineScope()),
    textFieldConfirmationDialogState: TextFieldConfirmationDialogState = TextFieldConfirmationDialogState(
        rememberCoroutineScope()
    ),
    handlePickImageFromGalleryResult: (String) -> Unit
) {
    val context = LocalContext.current

    if (isCameraPermissionGranted == true) {

        val photoPickerLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) {
            if (it != null) {
                handlePickImageFromGalleryResult(it.toString())
            }
        }

        Column(
            modifier.fillMaxSize()
        ) {

            TopBarTakePhoto(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                switchCameraLens = cameraState::setToggleCameraLens,
                cancelSession = navigateUp
            )

            CameraPreview(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = cameraState
            )

            BottomBarTakePhoto(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                captureImage = {
                    cameraState.captureImage(
                        context.applicationContext
                    )
                },
                pickImageFromGallery = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
            )

        }

        TextFieldConfirmationDialog(
            state = textFieldConfirmationDialogState,
            name = textFieldConfirmationDialogState.confirmationText,
            hintText = stringResource(R.string.masukan_nama_foto_disini),
            onValueNameChange = {
                if (it.length < 50) {
                    textFieldConfirmationDialogState.setText(it)
                }
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