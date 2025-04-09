package com.neotelemetrixgdscunand.kamekapp.presentation.ui.cacaoimagedetail.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.neotelemetrixgdscunand.kamekapp.domain.model.BoundingBox

@Composable
fun OverlayCompose(
    modifier: Modifier = Modifier,
    boundingBox: BoundingBox
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            OverlayView(context, null)
        },
        update = { view ->
            view.apply {
                setResults(listOf(boundingBox))
            }
        },
        onRelease = { view ->
            view.clear()
        }
    )
}