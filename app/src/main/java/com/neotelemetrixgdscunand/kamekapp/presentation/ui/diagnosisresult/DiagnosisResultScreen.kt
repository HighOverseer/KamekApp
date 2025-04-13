package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.rememberAsyncImagePainter
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.domain.model.DamageLevelCategory
import com.neotelemetrixgdscunand.kamekapp.domain.model.DiagnosisSession
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey90
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon55
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Orange90
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Yellow90
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.cacaoimagedetail.components.OverlayCompose
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.DetectedCacaoImageGrid
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.DiagnosisBottomContent
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.DiagnosisBottomContentLoading
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.DiagnosisDiseaseDetails
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.DiagnosisDiseaseDetailsLoading
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.DiagnosisResultHeaderSection
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.DiagnosisResultHeaderSectionLoading
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.DiagnosisResultTabSection
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.NavigateUpButton
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.PriceAnalysisContent
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.PriceAnalysisContentLoading
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.PriceAnalysisOverview
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.util.getBoundingBoxWithItsNameAsTheLabel
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.util.collectChannelWhenStarted
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.util.getValue

@Composable
fun DiagnosisResultScreen(
    modifier: Modifier = Modifier,
    viewModel: DiagnosisResultViewModel = hiltViewModel(),
    navigateUp: () -> Unit = {},
    showSnackbar: (String) -> Unit = { },
    navigateToCacaoImageDetail: (Int, Int?, String) -> Unit = { _, _, _ -> },
) {

    val uiState by viewModel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LaunchedEffect(true) {
        lifecycleOwner.collectChannelWhenStarted(
            viewModel.event
        ) { event ->

            when (event) {
                is DiagnosisResultUIEvent.OnToastMessage -> {
                    showSnackbar(event.message.getValue(context))
                }

                DiagnosisResultUIEvent.OnInputImageInvalid -> {
                    val message =
                        context.getString(R.string.input_image_is_invalid_no_cocoa_detected)
                    showSnackbar(message)
                    navigateUp()
                }
            }
        }
    }

    DiagnosisResultContent(
        modifier = modifier,
        navigateUp = navigateUp,
        uiState = uiState,
        navigateToCacaoImageDetail = { detectedCacaoId ->
            uiState.imagePreviewPath?.apply {
                navigateToCacaoImageDetail(
                    uiState.diagnosisSession.id,
                    detectedCacaoId,
                    this
                )
            }
        }
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiagnosisResultContent(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    uiState: DiagnosisResultUIState = DiagnosisResultUIState(),
    navigateToCacaoImageDetail: (Int?) -> Unit = { }
) {

    val imageAspectRatio = 1.26f
    val topToArrowMarginRatio = 0.04571f
    val listState = rememberLazyListState()

    val configuration = LocalConfiguration.current

    val topToArrowMargin = remember {
        configuration.screenHeightDp.dp * topToArrowMarginRatio
    }


    val groupedDetectedDisease = remember(uiState.diagnosisSession) {
        uiState.diagnosisSession.detectedCacaos.groupBy {
            it.disease
        }
    }

    val groupedDetectedDiseaseKeys = remember(groupedDetectedDisease) {
        groupedDetectedDisease.keys.toList()
    }

    val isExpandList = rememberSaveable(groupedDetectedDiseaseKeys) {
        List(groupedDetectedDiseaseKeys.size) { index ->
            val isInitialStateExpand = index == 0
            mutableStateOf(isInitialStateExpand)
        }
    }

    val outermostPaddingModifier = remember {
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    }

    val damageLevelCategoryInfo = remember {
        listOf(
            DamageLevelCategory.Low,
            DamageLevelCategory.Medium,
            DamageLevelCategory.High
        )
    }

    val bulletNumberModifier = remember {
        Modifier
            .wrapContentSize()
            .clip(CircleShape)
            .background(color = Maroon55)
            .sizeIn(minWidth = 24.dp, minHeight = 24.dp)
    }

    var isDiagnosisTabSelected by rememberSaveable { mutableStateOf(true) }

    Box {

        LazyColumn(
            modifier = modifier
                .align(Alignment.TopCenter)
                .background(color = Grey90),
            state = listState
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(imageAspectRatio)
                ) {

                    val image = rememberAsyncImagePainter(
                        model = uiState.imagePreviewPath,
                        placeholder = painterResource(R.drawable.ic_camera),
                        contentScale = ContentScale.Crop
                    )

                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                            .clickable {
                                navigateToCacaoImageDetail(null)
                            },
                        painter = image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )

                    val context = LocalContext.current
                    val adjustedBoundingBox = remember(uiState.diagnosisSession) {
                        uiState.diagnosisSession.detectedCacaos.map {
                            it.getBoundingBoxWithItsNameAsTheLabel(context)
                        }
                    }

                    OverlayCompose(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        boundingBoxes = adjustedBoundingBox
                    )
                }
            }

            item {
                if (uiState.isLoading) {
                    DiagnosisResultHeaderSectionLoading(
                        sessionName = uiState.diagnosisSession.title
                    )
                } else DiagnosisResultHeaderSection(
                    sessionName = uiState.diagnosisSession.title
                )
            }

            stickyHeader {
                DiagnosisResultTabSection(
                    isDiagnosisTabSelected = isDiagnosisTabSelected,
                    changeSelectedTab = {
                        val isReselectedTab = it == isDiagnosisTabSelected
                        if (!isReselectedTab) isDiagnosisTabSelected = it
                    }
                )
            }

            if (isDiagnosisTabSelected) {
                item {
                    Column(
                        modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp)
                            )
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    ) {
                        Text(
                            stringResource(R.string.penyakit_hama_yang_terdeteksi),
                            style = MaterialTheme.typography.titleMedium,
                            color = Black10
                        )
                        Spacer(Modifier.height(16.dp))

                    }
                }

                itemsIndexed(
                    items = groupedDetectedDiseaseKeys,
                    key = { _, it -> it }) { index, it ->

                    Column(
                        modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                            .then(
                                if (index == groupedDetectedDiseaseKeys.lastIndex) {
                                    Modifier.background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(
                                            bottomEnd = 8.dp,
                                            bottomStart = 8.dp
                                        )
                                    )
                                } else {
                                    Modifier.background(
                                        color = Color.White
                                    )
                                }
                            )
                            .padding(start = 16.dp, end = 16.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = bulletNumberModifier
                            ) {
                                Text(
                                    "${index + 1}",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.White
                                )
                            }

                            Spacer(Modifier.width(8.dp))

                            Text(
                                text = stringResource(it.nameResId),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontStyle = FontStyle.Italic
                                ),
                                color = Orange90
                            )
                        }

                        Spacer(Modifier.height(12.dp))

                        DetectedCacaoImageGrid(
                            detectedCacaos = groupedDetectedDisease[it] ?: emptyList(),
                            onItemClicked = navigateToCacaoImageDetail
                        )

                        if (index != groupedDetectedDiseaseKeys.lastIndex) {
                            Spacer(Modifier.height(8.dp))
                        }

                    }

                }

                item {
                    Spacer(Modifier.height(16.dp))

                    val context = LocalContext.current
                    if (uiState.isLoading) DiagnosisBottomContentLoading() else DiagnosisBottomContent(
                        preventions = remember(uiState.diagnosisSession) {
                            uiState.diagnosisSession.detectedCacaos.firstOrNull()?.let { item ->
                                item.disease.controlStringResId.map {
                                    context.getString(it)
                                }
                            } ?: emptyList()
                        },
                        solution = uiState.diagnosisSession.detectedCacaos.firstOrNull()?.disease?.solutionStringResId?.let {
                            stringResource(it)
                        } ?: ""
                    )
                }

                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        text = stringResource(R.string.rincian_penyakit),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.height(16.dp))
                }


                if (uiState.isLoading) {
                    item {
                        DiagnosisDiseaseDetailsLoading()
                    }
                } else {
                    itemsIndexed(
                        items = groupedDetectedDiseaseKeys,
                        key = { _, it -> it.ordinal }) { index, diseaseKey ->

                        DiagnosisDiseaseDetails(
                            isExpand = isExpandList[index].value,
                            toggleExpand = {
                                isExpandList[index].value = !isExpandList[index].value
                            },
                            modifier = Modifier.padding(bottom = 8.dp),
                            diseaseName = stringResource(diseaseKey.nameResId),
                            detectedCacaos = groupedDetectedDisease[diseaseKey] ?: emptyList(),
                            diseaseCause = stringResource(diseaseKey.causeStringResId),
                            diseaseSymptoms = stringResource(diseaseKey.symptomStringResId),
                            seedCondition = stringResource(diseaseKey.seedConditionStringResId),
                            onDetectedCacaoImageClicked = navigateToCacaoImageDetail
                        )
                    }
                }


            } else {
                if (uiState.isLoading) {
                    item {
                        PriceAnalysisContentLoading()
                    }
                } else {
                    item {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .background(color = Yellow90, shape = RoundedCornerShape(8.dp))
                                .padding(16.dp)
                        ) {
                            Row(
                                Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Top
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.ic_info),
                                    contentScale = ContentScale.Fit,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )

                                Spacer(Modifier.width(16.dp))

                                Text(
                                    stringResource(R.string.price_note),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Maroon55,
                                    textAlign = TextAlign.Start
                                )
                            }

                            Spacer(Modifier.height(24.dp))

                            Text(
                                stringResource(R.string.klik_untuk_prediksi_harga_dengan_porsi_yang_berbeda),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    textDecoration = TextDecoration.Underline,
                                    letterSpacing = (-0.175).sp
                                ),
                                color = Orange90,
                                textAlign = TextAlign.Start
                            )
                        }
                    }

                    item {
                        Spacer(Modifier.height(16.dp))

                        PriceAnalysisOverview()
                    }

                    item {
                        Spacer(Modifier.height(16.dp))

                        Text(
                            stringResource(R.string.rincian_prediksi_harga),
                            style = MaterialTheme.typography.titleMedium,
                            color = Black10,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )

                        Spacer(Modifier.height(16.dp))
                    }

                    items(
                        count = 3,
                        key = { it },
                        itemContent = {
                            PriceAnalysisContent(
                                modifier = outermostPaddingModifier,
                                damageLevelCategory = damageLevelCategoryInfo[it],
                                diagnosisSession = uiState.diagnosisSession,
                                onDetectedCacaoImageClicked = navigateToCacaoImageDetail
                            )
                            Spacer(Modifier.height(16.dp))
                        }
                    )
                }
            }
        }

        NavigateUpButton(
            modifier = Modifier
                .align(Alignment.TopStart),
            topToArrowMargin = topToArrowMargin,
            navigateUp = navigateUp
        )

    }
}

@Preview(showBackground = true, heightDp = 2000)
@Composable
private fun DiagnosisResultScreenPreview() {
    KamekAppTheme {
        DiagnosisResultContent(

        )
    }
}