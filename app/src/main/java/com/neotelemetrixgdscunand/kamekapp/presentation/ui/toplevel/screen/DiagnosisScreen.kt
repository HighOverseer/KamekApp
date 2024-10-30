package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme


@Composable
fun DiagnosisScreen(modifier: Modifier = Modifier) {
    Text("Diagnosis")
}

@Preview
@Composable
private fun DiagnosisScreenPreview() {
    KamekAppTheme {
        DiagnosisScreen()
    }

}