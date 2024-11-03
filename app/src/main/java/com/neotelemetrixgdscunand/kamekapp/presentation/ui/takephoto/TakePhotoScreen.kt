package com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto

import androidx.camera.core.ImageCapture
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.MainActivity
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon55
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.component.BottomBarTakePhoto
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.component.CameraPreview
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.component.InputPhotoNameDialog
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.component.TertiaryButton
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.component.TopBarTakePhoto
import java.io.File

@Composable
fun TakePhotoScreen(
    modifier: Modifier = Modifier,
    isCameraPermissionGranted:Boolean?,
    showSnackBar:(String) -> Unit = {},
    navigateUp:() -> Unit = {},
    navigateToResult:() -> Unit = {}
) {

    var isDialogShowed by remember { mutableStateOf(false) }
    var sessionName by remember { mutableStateOf("") }

    var isUsingBackCamera by remember { mutableStateOf(true) }
    val imageCapture = remember {
        ImageCapture.Builder()
            .build()
    }
    var capturedPhotoFile: File? by remember {
        mutableStateOf(null)
    }


    val context = LocalContext.current

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
                        onSuccess = { file ->
                            capturedPhotoFile = file
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
                bottomBarStartMarginRatio = bottomBarStartMarginRatio,
                bottomBarEndMarginRatio = bottomBarEndMarginRatio
            )

        }

        val invalidNameMessage = stringResource(R.string.nama_yang_dimasukkan_tidak_valid)
        val onSubmit by rememberUpdatedState{
            if(sessionName.isBlank()){
                showSnackBar(invalidNameMessage)
            }else{
                capturedPhotoFile = renameFile(capturedPhotoFile, sessionName)
                println(capturedPhotoFile?.name)
                navigateToResult()
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