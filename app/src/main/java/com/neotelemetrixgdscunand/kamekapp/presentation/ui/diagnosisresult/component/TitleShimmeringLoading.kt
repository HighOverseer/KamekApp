package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.util.shimmeringEffect

@Composable
fun TitleShimmeringLoading(modifier: Modifier = Modifier, height:Dp = 17.dp, widthRatio : Float = 0.2f) {
    Box(
        modifier = modifier
            .fillMaxWidth(widthRatio)
            .height(height)
            .shimmeringEffect()
    )
}

@Preview
@Composable
private fun TitleShimmeringLoadingPreview() {
    KamekAppTheme {
        TitleShimmeringLoading()
    }
}