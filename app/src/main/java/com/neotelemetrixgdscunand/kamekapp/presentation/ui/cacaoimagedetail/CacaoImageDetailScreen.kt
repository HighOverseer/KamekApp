package com.neotelemetrixgdscunand.kamekapp.presentation.ui.cacaoimagedetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.domain.model.BoundingBox
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.cacaoimagedetail.components.OverlayCompose
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.NavigateUpButton

@Composable
fun CacaoImageDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: CacaoImageDetailViewModel = hiltViewModel(),
    navigateUp: () -> Unit = { }
) {

    CacaoImageDetailContent(
        modifier = modifier,
        boundingBox = viewModel.boundingBox,
        navigateUp = navigateUp,
        imagePath = viewModel.imagePath
    )
}

@Composable
fun CacaoImageDetailContent(
    modifier: Modifier = Modifier,
    imagePath: String = "",
    navigateUp: () -> Unit = { },
    boundingBox: BoundingBox
) {
    val topToArrowMarginRatio = 0.04571f
    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp.dp
    val topToArrowMargin = screenHeightDp * topToArrowMarginRatio

    val boundingBoxes = remember(boundingBox) {
        listOf(boundingBox)
    }

    Box(Modifier.fillMaxSize()) {
        AsyncImage(
            modifier = modifier
                .fillMaxSize()
                .align(Alignment.Center),
            model = imagePath,
            placeholder = painterResource(R.drawable.ic_camera),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            alignment = Alignment.Center
        )

        OverlayCompose(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            boundingBoxes = boundingBoxes
        )

        NavigateUpButton(
            modifier = Modifier
                .align(Alignment.TopStart),
            topToArrowMargin = topToArrowMargin,
            navigateUp = navigateUp
        )
    }
}