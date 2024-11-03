package com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon55

@Composable
fun BottomBarTakePhoto(
    modifier: Modifier = Modifier,
    bottomBarStartMarginRatio:Float = 0.11864f,
    bottomBarEndMarginRatio:Float = 0.09864f,
    onCaptureImage:() -> Unit = {},
) {
    val parentModifier = remember {
        modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.White)
            .padding(vertical = 24.dp)
    }

    Row(
        modifier = parentModifier
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
                    ).clickable(onClick = onCaptureImage)
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
                TertiaryButton(
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

@Preview
@Composable
private fun BottomBarTakePhotoPreview() {
    KamekAppTheme {
        BottomBarTakePhoto()
    }
}