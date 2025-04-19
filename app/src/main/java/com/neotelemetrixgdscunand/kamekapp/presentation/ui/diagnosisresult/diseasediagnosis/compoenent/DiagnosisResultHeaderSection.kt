package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.diseasediagnosis.compoenent

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon45
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive

@Composable
fun DiagnosisResultHeaderSection(
    modifier: Modifier = Modifier,
    sessionName: String = "Kakao Pak Tono",
    isLoadingProvider: () -> Boolean = { false },
    isLocalNavigateUpButtonVisibleProvider : () -> Boolean = { false },
    navigateUp: () -> Unit = {}
) {
    if (isLoadingProvider()) {
        DiagnosisResultHeaderSectionLoading(
            modifier = modifier,
            sessionName = sessionName
        )

    } else Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Crossfade(isLocalNavigateUpButtonVisibleProvider()) {
                if(it){
                    Icon(
                        modifier = Modifier
                            .clickable(onClick = navigateUp)
                            .padding(end = 12.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left),
                        contentDescription = null,
                        tint = Black10
                    )
                }
            }
            Text(
                sessionName,
                style = MaterialTheme.typography.headlineSmall,
                color = Black10
            )
        }

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


@Preview(showBackground = true)
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