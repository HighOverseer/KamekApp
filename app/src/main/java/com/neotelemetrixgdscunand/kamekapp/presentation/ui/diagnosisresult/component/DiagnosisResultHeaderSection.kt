package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme

@Composable
fun DiagnosisResultHeaderSection(
    modifier: Modifier = Modifier,
    sessionName: String = "",
) {
    Column(
        modifier = modifier
    ) {
        Text(
            sessionName,
            style = MaterialTheme.typography.headlineSmall,
            color = Black10
        )
    }
}

@Composable
fun DiagnosisResultHeaderSectionLoading(
    modifier: Modifier = Modifier,
    sessionName: String
) {
    Column(
        modifier = modifier
    ) {

        Text(
            sessionName,
            style = MaterialTheme.typography.headlineSmall,
            color = Black10
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
        DiagnosisResultHeaderSectionLoading(sessionName = "Kakao Kebun Pak Tono")
    }

}