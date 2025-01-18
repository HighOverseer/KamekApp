package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme

@Composable
fun DiagnosisResultHeaderSection(
    modifier: Modifier = Modifier,
    sessionName:String = "",
    diseaseName:String = "",
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 24.dp, end = 16.dp)
    ){
        Text(
            sessionName,
            style = MaterialTheme.typography.headlineSmall,
            color = Black10
        )

//        Spacer(Modifier.height(16.dp))
//
//        Text(
//            stringResource(R.string.penyakit),
//            style = MaterialTheme.typography.labelMedium,
//            color = Grey60
//        )
//
//        Spacer(Modifier.height(8.dp))
//
//        Text(
//            diseaseName,
//            style = MaterialTheme.typography.titleMedium,
//            color = Black10
//        )
    }
}

@Composable
fun DiagnosisResultHeaderSectionLoading(
    modifier: Modifier = Modifier,
    sessionName: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 24.dp, end = 16.dp)
    ){

        Text(
            sessionName,
            style = MaterialTheme.typography.headlineSmall,
            color = Black10
        )

//        Spacer(Modifier.height(16.dp))
//
//
//        Text(
//            stringResource(R.string.penyakit),
//            style = MaterialTheme.typography.labelMedium,
//            color = Grey60
//        )
//
//        Spacer(Modifier.height(8.dp))
//
//        DescriptionShimmeringLoading(
//            lineHeight = 17.dp,
//            lastLineWidthRatio = 0.7f,
//            lineCount = 2
//        )
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
        DiagnosisResultHeaderSectionLoading(sessionName = "Kakao Kebun Pak Tono")
    }

}