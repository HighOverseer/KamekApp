package com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto

import android.net.Uri
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toFile
import com.neotelemetrixgdscunand.kamekapp.MainActivity
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.component.BottomBarTakePhoto
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.component.CameraPreview
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.component.InputPhotoNameDialog
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.component.TopBarTakePhoto
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.util.FileManager
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.util.ImageCompressor
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun TakePhotoScreen(
    modifier: Modifier = Modifier,
    isCameraPermissionGranted:Boolean?,
    showSnackBar:(String) -> Unit = {},
    navigateUp:() -> Unit = {},
    navigateToResult:() -> Unit = {}
) {

    val context = LocalContext.current

    val imageCompressor = remember {
        ImageCompressor(context)
    }

    val fileManager = remember {
        FileManager(context)
    }

    val coroutineScope = rememberCoroutineScope()

    var isDialogShowed by remember { mutableStateOf(false) }
    var sessionName by remember { mutableStateOf("") }

    var isUsingBackCamera by remember { mutableStateOf(true) }
    val imageCapture = remember {
        ImageCapture.Builder()
            .build()
    }
    var capturedPhotoUri: Uri? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(true) {
        if(isCameraPermissionGranted == null && context is MainActivity){
            context.checkCameraPermission()
        }

    }

    val permissionDeniedMessage = stringResource(R.string.fitur_tidak_bisa_diakses)

    LaunchedEffect(isCameraPermissionGranted) {
        if(isCameraPermissionGranted == false){
            showSnackBar(
                permissionDeniedMessage
            )
        }
    }

    val cameraPreviewHeightRatio = 0.8f
    val topBarHeightRatio = 0.089f
    val bottomBarStartMarginRatio = 0.11864f
    val bottomBarEndMarginRatio = 0.09864f


    if(isCameraPermissionGranted == true){

        var isCameraOpen by remember { mutableStateOf(true) }
        val photoPickerLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) {
            capturedPhotoUri = it
            if(capturedPhotoUri != null){
                isDialogShowed = true
                isCameraOpen = false
            }
        }


        Column(
            modifier.fillMaxSize()
        ){

            TopBarTakePhoto(
                topBarHeightRatio = topBarHeightRatio,
                switchCameraLens = {
                    isUsingBackCamera = !isUsingBackCamera
                },
                cancelSession = navigateUp
            )

            CameraPreview(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(cameraPreviewHeightRatio),
                isUsingBackCamera = isUsingBackCamera,
                imageCapture,
                isCameraOpen
            )

            BottomBarTakePhoto(
                onCaptureImage = {
                    captureImage(
                        imageCapture,
                        context,
                        onSuccess = { uri ->
                            capturedPhotoUri = uri
                            isDialogShowed = true
                            isCameraOpen = false
                        },
                        onError = { e ->
                            showSnackBar(
                                context.getString(
                                    R.string.error,
                                    e.message.toString()
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

        val invalidNameMessage = stringResource(R.string.nama_yang_dimasukkan_tidak_valid)
        val onSubmit:() -> Unit by rememberUpdatedState{
            if(sessionName.isBlank()){
                showSnackBar(invalidNameMessage)
            }else{
                coroutineScope.launch {
                    val compressedImage = imageCompressor.compressImage(capturedPhotoUri)
                    fileManager.saveImage(capturedPhotoUri, compressedImage, sessionName)

                    isDialogShowed = false
                    navigateToResult()
                }
            }
        }

        InputPhotoNameDialog(
            isShowDialog = isDialogShowed,
            name = sessionName,
            onValueNameChange = {
                if(it.length < 50){
                    sessionName = it
                }
            },
            onDismiss = {
                coroutineScope.launch {
                    fileManager.deleteFile(capturedPhotoUri)
                }
                isDialogShowed = false
                isCameraOpen = true
            },
            onSubmit = onSubmit
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