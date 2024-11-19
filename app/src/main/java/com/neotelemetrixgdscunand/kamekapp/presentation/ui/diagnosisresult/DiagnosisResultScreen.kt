package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.data.DummyUtils
import com.neotelemetrixgdscunand.kamekapp.domain.model.CacaoDisease
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey90
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.DiagnosisBottomContent
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.DiagnosisBottomContentLoading
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.DiagnosisResultHeaderSection
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.DiagnosisResultHeaderSectionLoading
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.DiagnosisResultTabSection
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.DiagnosisTopContent
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.DiagnosisTopContentLoading
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.NavigateUpButton
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.PriceAnalysisContent
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component.PriceAnalysisContentLoading
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.util.ImageClassifierHelper
import java.io.File

@Composable
fun DiagnosisResultScreen(
    modifier: Modifier = Modifier,
    viewModel: DiagnosisResultViewModel = hiltViewModel(),
    navigateUp:() -> Unit = {},
    imagePath:String,
    outputId:Int? = null,
) {

        DiagnosisResultContent(
            modifier = modifier,
            navigateUp = navigateUp,
            imagePath = imagePath,
            onSaveDiagnosisResult = viewModel::saveDiagnosisResult,
            outputId = outputId,
            onGetDiagnosisOutput = viewModel::getDiagnosisResult
        )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiagnosisResultContent(
    modifier: Modifier = Modifier,
    navigateUp:() -> Unit = {},
    imagePath:String,
    onSaveDiagnosisResult:(DiagnosisOutput, String, String) -> Unit = {_, _, _ ->},
    outputId:Int?,
    onGetDiagnosisOutput:(Int) -> DiagnosisOutput,
) {
    var isLoading by remember {
        mutableStateOf(true)
    }

    val imageAspectRatio = 1.26f
    val topToArrowMarginRatio = 0.04571f
    val listState = rememberLazyListState()

    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp.dp

    val topToArrowMargin = screenHeightDp * topToArrowMarginRatio

    var isDiagnosisTabSelected by remember {
        mutableStateOf(true)
    }

    val imageFile = remember(imagePath) {
        if(imagePath.first() == 'h'){
            null
        }else File(imagePath)
    }


    val context = LocalContext.current

    val imageClassifierHelper = remember {
        ImageClassifierHelper(context)
    }

    var diagnosisOutput by remember {
        mutableStateOf(
            DiagnosisOutput(
                disease = CacaoDisease.NONE,
                damageLevel = 0f,
                sellPrice = 2000f
            )
        )
    }

    val image = rememberAsyncImagePainter(
        model = imageFile ?: imagePath,
        placeholder = painterResource(R.drawable.ic_camera),
        contentScale = ContentScale.Crop
    )

    val sessionName by remember(imageFile){
        derivedStateOf{
            imageFile?.nameWithoutExtension
                ?: DummyUtils.diagnosisDummyNameMap[outputId]
                ?: "Dummy Session Name"
        }
    }

    LaunchedEffect(Unit) {
        if(outputId == null){

            //should not null
            if(imageFile == null) return@LaunchedEffect

            diagnosisOutput = imageClassifierHelper.classify(
                imageFile.toUri()
            )
            onSaveDiagnosisResult(
                diagnosisOutput,
                imagePath,
                imageFile.nameWithoutExtension,
            )
            isLoading = false
        }else{
            diagnosisOutput = onGetDiagnosisOutput(outputId)
            isLoading = false
        }

    }

    Box{
        LazyColumn(
            modifier = modifier
                .align(Alignment.TopCenter)
                .background(color = Grey90),
            state = listState
        ){
            item{
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(imageAspectRatio)
                ){
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .align(Alignment.Center),
                        painter = image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }

            item {
                if(isLoading){
                    DiagnosisResultHeaderSectionLoading(
                        sessionName = sessionName
                    )
                }else DiagnosisResultHeaderSection(
                    sessionName = sessionName,
                    diseaseName = stringResource(diagnosisOutput.disease.nameResId)
                )
            }

            stickyHeader {
                DiagnosisResultTabSection(
                    isDiagnosisTabSelected = isDiagnosisTabSelected,
                    changeSelectedTab = {
                        isDiagnosisTabSelected = it
                    }
                )
            }

            if(isDiagnosisTabSelected){
                item {
                    if(isLoading) DiagnosisTopContentLoading() else DiagnosisTopContent(
                        diseaseCause = stringResource(diagnosisOutput.disease.causeStringResId),
                        diseaseSymptoms = stringResource(diagnosisOutput.disease.symptomStringResId),
                        seedCondition = stringResource(diagnosisOutput.disease.seedConditionStringResId)
                    )
                }

                item {
                    if(isLoading) DiagnosisBottomContentLoading() else DiagnosisBottomContent(
                        preventions = remember {
                            diagnosisOutput.disease.controlStringResId.map {
                                context.getString(it)
                            }
                        },
                        solution = stringResource(diagnosisOutput.disease.solutionStringResId)
                    )
                }
            }else{
                item {
                    if(isLoading) PriceAnalysisContentLoading() else PriceAnalysisContent(
                        sellPrice = diagnosisOutput.sellPrice,
                        damageLevel = diagnosisOutput.damageLevel
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

@Preview(showBackground = true, heightDp = 1500)
@Composable
private fun DiagnosisResultScreenPreview() {
    KamekAppTheme {
        DiagnosisResultScreen(
            imagePath = ""
        )
    }
}