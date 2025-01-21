package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.data.DummyUtils
import com.neotelemetrixgdscunand.kamekapp.domain.model.CacaoDisease
import com.neotelemetrixgdscunand.kamekapp.domain.model.DamageLevelCategory
import com.neotelemetrixgdscunand.kamekapp.domain.model.getDetectedDiseaseDummies
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey90
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon55
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Orange90
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Yellow90
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
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.util.ImageClassifierHelper
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home.DetectedCacaoImageGrid
import java.io.File

@Composable
fun DiagnosisResultScreen(
    modifier: Modifier = Modifier,
    viewModel: DiagnosisResultViewModel = hiltViewModel(),
    navigateUp:() -> Unit = {},
    imagePath:String,
    outputId:Int? = null,
) {

    val context = LocalContext.current

    val imageClassifierHelper = remember {
        ImageClassifierHelper(context)
    }
    var isLoading by remember {
        mutableStateOf(true)
    }

    DiagnosisResultContent(
        modifier = modifier,
        navigateUp = navigateUp,
        imagePath = imagePath,
        onSaveDiagnosisResult = viewModel::saveDiagnosisResult,
        outputId = outputId,
        classify = imageClassifierHelper::classify,
        onGetDiagnosisOutput = viewModel::getDiagnosisResult,
        isLoadingProvider = { isLoading },
        setIsLoading = {
            isLoading = it
        }
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
    classify: suspend (Uri) -> DiagnosisOutput,
    onGetDiagnosisOutput:(Int) -> DiagnosisOutput,
    isLoadingProvider:()->Boolean = { false },
    setIsLoading:(Boolean)->Unit = {_ -> }
) {


    val imageAspectRatio = 1.26f
    val topToArrowMarginRatio = 0.04571f
    val listState = rememberLazyListState()

    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp.dp

    val topToArrowMargin = screenHeightDp * topToArrowMarginRatio

    var isDiagnosisTabSelected by remember {
        mutableStateOf(false)
    }

    val imageFile = remember(imagePath) {
        val isInPreview = imagePath.isBlank()

        if(isInPreview){
            return@remember null
        }

        if(imagePath.first() == 'h') {
            null
        }
        else File(imagePath)
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
        mutableStateOf("null")
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

    LaunchedEffect(Unit) {
        if(outputId == null){

            //should not null
            if(imageFile == null) return@LaunchedEffect

            diagnosisOutput = classify(
                imageFile.toUri()
            )
            onSaveDiagnosisResult(
                diagnosisOutput,
                imagePath,
                imageFile.nameWithoutExtension,
            )
            setIsLoading(false)
        }else{
            diagnosisOutput = onGetDiagnosisOutput(outputId)
            setIsLoading(false)
        }
    }

    val bulletNumberModifier = remember {
        Modifier
            .wrapContentSize()
            .clip(CircleShape)
            .background(color = Maroon55)
            .sizeIn(minWidth = 24.dp, minHeight = 24.dp)
    }

    Box{

        val detectedDisease = remember {
            getDetectedDiseaseDummies()
        }

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
                        contentScale = ContentScale.Crop,
                    )
                }
            }

            item {
                if(isLoadingProvider()){
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
                item{
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

                itemsIndexed(items = detectedDisease, key = { _, it -> it.id }){ index, it ->
                    Column(
                        modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                            .then(
                                if (index == detectedDisease.lastIndex) {
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
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    )  {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = bulletNumberModifier
                            ){
                                Text(
                                    "${index+1}",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Color.White
                                )
                            }

                            Spacer(Modifier.width(8.dp))

                            Text(
                                text = stringResource(it.disease.nameResId) ,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontStyle = FontStyle.Italic
                                ),
                                color = Orange90
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                        DetectedCacaoImageGrid(
                            detectedCacaos = it.detectedCacaos
                        )

                        if(index != detectedDisease.lastIndex){
                            Spacer(Modifier.height(8.dp))
                        }

                    }

                }

                item {
                    Spacer(Modifier.height(16.dp))

                    val context = LocalContext.current
                    if(isLoadingProvider()) DiagnosisBottomContentLoading() else DiagnosisBottomContent(
                        preventions = remember {
                            diagnosisOutput.disease.controlStringResId.map {
                                context.getString(it)
                            }
                        },
                        solution = stringResource(diagnosisOutput.disease.solutionStringResId)
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


                if(isLoadingProvider()){
                    item {
                        DiagnosisDiseaseDetailsLoading()
                    }
                }else{
                    items(items = detectedDisease, key = { it.hashCode() }){
                        val isInitiallyExpanded = it == detectedDisease.first()

                        DiagnosisDiseaseDetails(
                            initiallyExpanded = isInitiallyExpanded,
                            modifier = Modifier.padding(bottom = 8.dp),
                            diseaseName = stringResource(it.disease.nameResId),
                            detectedCacaos = it.detectedCacaos,
                            diseaseCause = stringResource(diagnosisOutput.disease.causeStringResId),
                            diseaseSymptoms = stringResource(diagnosisOutput.disease.symptomStringResId),
                            seedCondition = stringResource(diagnosisOutput.disease.seedConditionStringResId)
                        )
                    }
                }


            }else{
                if(isLoadingProvider()){
                    item {
                        PriceAnalysisContentLoading()
                    }
                }else{
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

                    item{
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
                                damageLevelCategory = damageLevelCategoryInfo[it]
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






@Preview(showBackground = true, heightDp = 3500)
@Composable
private fun DiagnosisResultScreenPreview() {
    KamekAppTheme {
        DiagnosisResultContent(
            modifier = Modifier,
            navigateUp = {},
            imagePath = "",
            onSaveDiagnosisResult = {_, _, _ ->},
            outputId = null,
            classify = {_ -> DiagnosisOutput(disease = CacaoDisease.NONE, damageLevel = 0f, sellPrice = 2000f)},
            onGetDiagnosisOutput = { _ -> DiagnosisOutput(disease = CacaoDisease.NONE, damageLevel = 0f, sellPrice = 2000f)}
        )
    }
}