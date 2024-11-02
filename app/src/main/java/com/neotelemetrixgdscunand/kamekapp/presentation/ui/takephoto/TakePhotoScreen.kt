package com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto

import android.graphics.Camera
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.MainActivity
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon55
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.diagnosishistory.TakePhotoSection

@Composable
fun TakePhotoScreen(
    modifier: Modifier = Modifier,
    isCameraPermissionGranted:Boolean?,
    showSnackBar:(String) -> Unit = {},
) {
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

    val topBarModifier = remember {
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(topBarHeightRatio)
            .background(color = Color.White)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    }

    if(isCameraPermissionGranted == true){
        Column(
            modifier.fillMaxSize()
        ){
            Row(
                modifier = topBarModifier,
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(
                        id = R.drawable.ic_close
                    ),
                    contentDescription = stringResource(R.string.cancel_action)
                )

                Image(
                    imageVector = ImageVector
                        .vectorResource(R.drawable.ic_switch_camera),
                    contentDescription = stringResource(R.string.switch_camera)
                )
            }
            CameraPreview(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(cameraPreviewHeightRatio)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.White)
                    .padding(vertical = 24.dp),
            ) {
                BoxWithConstraints(
                    Modifier.fillMaxSize()
                ){
                    val bottomBarMarginStart = this@BoxWithConstraints.maxWidth * bottomBarStartMarginRatio
                    val bottomBarMarginEnd = this@BoxWithConstraints.maxWidth * bottomBarEndMarginRatio

                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                    ){
                        Spacer(
                            Modifier.width(bottomBarMarginStart)
                        )

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            Image(
                                modifier = Modifier,
                                imageVector = ImageVector
                                    .vectorResource(R.drawable.ic_gallery),
                                contentDescription = stringResource(R.string.get_image_from_gallery),
                            )
                            Text(
                                stringResource(R.string.upload),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }

                    }


                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(60.dp)
                            .clip(CircleShape)
                            .border(
                                shape = CircleShape,
                                color = Maroon55,
                                width = 4.dp
                            )
                    ){
                        Box(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(8.dp)
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(color = Maroon55)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                    ) {
                        SecondaryButton(
                            modifier = Modifier
                                .height(33.dp)
                                .alignByBaseline()
                        )
                        Spacer(
                            Modifier.width(bottomBarMarginEnd)
                        )
                    }

            }


            }

        }
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