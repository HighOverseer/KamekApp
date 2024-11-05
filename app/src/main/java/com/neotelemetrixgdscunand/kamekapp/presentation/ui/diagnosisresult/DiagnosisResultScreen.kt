package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.R
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
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiagnosisResultScreen(
    modifier: Modifier = Modifier,
    navigateUp:() -> Unit = {}
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

    val preventionsDummy = remember {
        getPreventionsDummy()
    }


    LaunchedEffect(Unit) {
        delay(3000L)
        isLoading = false
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
                            .align(Alignment.Center),
                        painter = painterResource(
                            R.drawable.diagnosis_item_dummy,
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }

            item {
                if(isLoading){
                    DiagnosisResultHeaderSectionLoading()
                }else DiagnosisResultHeaderSection()
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
                    if(isLoading) DiagnosisTopContentLoading() else DiagnosisTopContent()
                }

                item {
                    if(isLoading) DiagnosisBottomContentLoading() else DiagnosisBottomContent(
                        preventionsList = preventionsDummy
                    )
                }
            }else{
                item {
                    if(isLoading) PriceAnalysisContentLoading() else PriceAnalysisContent()
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


fun getPreventionsDummy() = listOf(
    "Sanitasi Kebun: Lakukan pembersihan kebun secara rutin, singkirkan buah yang terinfeksi dan bagian tanaman yang mati.",
    "Pangkas Pohon Secara Teratur: Pangkas cabang-cabang berlebih agar sirkulasi udara meningkat dan kelembapan di sekitar buah berkurang.",
    "Drainase yang Baik: Pastikan area perkebunan memiliki sistem drainase yang baik untuk mencegah genangan air yang mendukung pertumbuhan jamur.",
    "Pemilihan Varietas: Tanam varietas kakao yang lebih tahan terhadap serangan Phytophthora, seperti varietas hibrida."
)

@Preview(showBackground = true, heightDp = 1500)
@Composable
private fun DiagnosisResultScreenPreview() {
    KamekAppTheme {
        DiagnosisResultScreen()
    }
}