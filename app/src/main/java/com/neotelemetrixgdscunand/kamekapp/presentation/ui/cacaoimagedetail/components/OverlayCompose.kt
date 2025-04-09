package com.neotelemetrixgdscunand.kamekapp.presentation.ui.cacaoimagedetail.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.domain.model.BoundingBox
import com.neotelemetrixgdscunand.kamekapp.domain.model.DetectedCacao

@Composable
fun OverlayCompose(
    modifier: Modifier = Modifier,
    boundingBoxes: List<BoundingBox>
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            OverlayView(context, null)
        },
        update = { view ->
            view.apply {
                setResults(boundingBoxes)
            }
        },
        onRelease = { view ->
            view.clear()
        }
    )
}