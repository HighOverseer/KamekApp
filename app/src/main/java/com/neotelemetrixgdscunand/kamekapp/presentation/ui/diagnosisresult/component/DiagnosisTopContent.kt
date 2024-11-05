package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme

@Composable
fun DiagnosisTopContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
    ) {


        PrimaryDescription(
            title = stringResource(R.string.penyebab),
            description = stringResource(R.string.dummy_cause)
        )

        Spacer(Modifier.height(24.dp))

        PrimaryDescription(
            title = stringResource(R.string.gejala),
            description = stringResource(R.string.dummy_cause)
        )

        Spacer(Modifier.height(24.dp))

        SecondaryDescription(
            title = stringResource(R.string.kondisi_biji),
            description = stringResource(R.string.dummy_seed_condition)
        )
    }
}

@Composable
fun DiagnosisTopContentLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
    ) {

        TitleShimmeringLoading(
            height = 17.dp,
            widthRatio = 0.3f
        )

        Spacer(Modifier.height(8.dp))

        DescriptionShimmeringLoading(
            lineHeight = 17.dp,
            lineCount = 2,
            lastLineWidthRatio = 0.7f
        )

        Spacer(Modifier.height(24.dp))

        TitleShimmeringLoading(
            height = 17.dp,
            widthRatio = 0.3f
        )

        Spacer(Modifier.height(8.dp))

        DescriptionShimmeringLoading(
            lineHeight = 17.dp,
            lineCount = 3,
            lastLineWidthRatio = 0.7f
        )

        Spacer(Modifier.height(24.dp))

        TitleShimmeringLoading(
            height = 17.dp,
            widthRatio = 0.4f
        )

        Spacer(Modifier.height(8.dp))

        DescriptionShimmeringLoading(
            lineHeight = 17.dp,
            lineCount = 5,
            lastLineWidthRatio = 0.7f
        )
    }
}

@Preview
@Composable
private fun DiagnosisTopContentPreview() {
    KamekAppTheme {
        DiagnosisTopContent()
    }

}