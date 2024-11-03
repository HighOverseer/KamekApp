package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey60
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme

@Composable
fun SecondaryDescription(
    modifier: Modifier = Modifier,
    title:String,
    description:String
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
        style = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 14.sp
        ),
        color = Grey60
    )


}

@Preview
@Composable
private fun SecondaryDescriptionPreview() {
    KamekAppTheme {
        SecondaryDescription(title = "", description = "")
    }
}