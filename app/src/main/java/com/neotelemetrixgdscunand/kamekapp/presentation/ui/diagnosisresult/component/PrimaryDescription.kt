package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey60
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme

@Composable
fun PrimaryDescription(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {
    Text(
        modifier = modifier,
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = Black10
    )

    Spacer(Modifier.height(8.dp))

    Text(
        description,
        style = MaterialTheme.typography.labelMedium,
        color = Grey60
    )
}

@Preview
@Composable
private fun PrimaryDescriptionPreview() {
    KamekAppTheme {
        PrimaryDescription(title = "", description = "")
    }
}