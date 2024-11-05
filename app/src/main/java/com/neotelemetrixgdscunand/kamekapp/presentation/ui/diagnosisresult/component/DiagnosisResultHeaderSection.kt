package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey60
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme

@Composable
fun DiagnosisResultHeaderSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 24.dp, end = 16.dp)
    ){
        Text(
            stringResource(R.string.dummy_image_name),
            style = MaterialTheme.typography.headlineSmall,
            color = Black10
        )

        Spacer(Modifier.height(16.dp))

        Text(
            stringResource(R.string.penyakit),
            style = MaterialTheme.typography.labelMedium,
            color = Grey60
        )

        Spacer(Modifier.height(8.dp))

        Text(
            stringResource(R.string.dummy_disease),
            style = MaterialTheme.typography.titleMedium,
            color = Black10
        )
    }
}

@Composable
fun DiagnosisResultHeaderSectionLoading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 24.dp, end = 16.dp)
    ){

        Text(
            stringResource(R.string.dummy_image_name),
            style = MaterialTheme.typography.headlineSmall,
            color = Black10
        )

        Spacer(Modifier.height(16.dp))

        TitleShimmeringLoading(
            widthRatio = 0.2f,
            height = 19.dp
        )

        Spacer(Modifier.height(8.dp))

        DescriptionShimmeringLoading(
            lineHeight = 17.dp,
            lastLineWidthRatio = 0.7f,
            lineCount = 2
        )
    }
}


@Preview
@Composable
private fun DiagnosisResultHeaderSectionPreview() {
    KamekAppTheme {
        DiagnosisResultHeaderSection()
    }

}

@Preview
@Composable
private fun DiagnosisResultHeaderSectionLoadingPreview() {
    KamekAppTheme {
        DiagnosisResultHeaderSectionLoading()
    }

}