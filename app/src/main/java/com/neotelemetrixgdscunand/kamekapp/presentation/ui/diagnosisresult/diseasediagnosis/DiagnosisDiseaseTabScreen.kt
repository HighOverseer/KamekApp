package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.diseasediagnosis

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.domain.model.CocoaDisease
import com.neotelemetrixgdscunand.kamekapp.domain.model.DetectedCocoa
import com.neotelemetrixgdscunand.kamekapp.presentation.dui.DiagnosisSessionDui
import com.neotelemetrixgdscunand.kamekapp.presentation.mapper.CocoaDiseaseMapper
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.diseasediagnosis.component.DetectedCacaoDiseasePreviewSection
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.diseasediagnosis.component.DiagnosisBottomContent
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.diseasediagnosis.component.DiagnosisDiseaseDetails
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap

@Composable
fun DiagnosisDiseaseTabScreen(
    modifier: Modifier = Modifier,
    groupedDetectedDisease: ImmutableMap<CocoaDisease, ImmutableList<DetectedCocoa>> =
        mutableMapOf<CocoaDisease, ImmutableList<DetectedCocoa>>().toImmutableMap(),
    navigateToCacaoImageDetail: (Int) -> Unit = { },
    diagnosisSessionDui: DiagnosisSessionDui = DiagnosisSessionDui(),
    isLoadingProvider: () -> Boolean = { false },
    isItemExpandProvider: (Int) -> Boolean = { false },
    toggleItemExpand: (Int) -> Unit = { },
) {
    DetectedCacaoDiseasePreviewSection(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        groupedDetectedDisease = groupedDetectedDisease,
        navigateToCacaoImageDetail = navigateToCacaoImageDetail
    )

    Spacer(Modifier.height(16.dp))

    val context = LocalContext.current
    val solutionResId = remember(diagnosisSessionDui) {
        CocoaDiseaseMapper.getDefaultSolutionResIdOfInfectedDiseases(
            diagnosisSessionDui.detectedCocoas
        )
    }

    val preventions = remember(diagnosisSessionDui) {
        CocoaDiseaseMapper.getDefaultPreventionsResIdOfInfectedDiseases(
            diagnosisSessionDui.detectedCocoas
        ).let { context.getString(it).split("\n") }.toImmutableList()
    }

    DiagnosisBottomContent(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        preventions = preventions,
        solution = stringResource(solutionResId),
        isLoadingProvider = isLoadingProvider
    )

    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = stringResource(R.string.rincian_penyakit),
        style = MaterialTheme.typography.titleMedium
    )

    Spacer(Modifier.height(16.dp))

    DiagnosisDiseaseDetails(
        groupedDetectedDisease = groupedDetectedDisease,
        isItemExpandProvider = isItemExpandProvider,
        toggleItemExpand = toggleItemExpand,
        navigateToCacaoImageDetail = navigateToCacaoImageDetail,
        isLoadingProvider = isLoadingProvider
    )
}