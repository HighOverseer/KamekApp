package com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.component

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
fun PriceAnalysisContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
    ){
        PrimaryDescription(
            title = stringResource(R.string.harga_jual),
            description = stringResource(R.string.dummy_selling_price)
        )

        Spacer(Modifier.height(24.dp))

        PrimaryDescription(
            title = stringResource(R.string.varietas),
            description = stringResource(R.string.dummy_variety)
        )

        Spacer(Modifier.height(24.dp))

        SecondaryDescription(
            title = stringResource(R.string.bobot_buah),
            description = stringResource(R.string.dummy_fruit_weight)
        )

        Spacer(Modifier.height(24.dp))

        SecondaryDescription(
            title = stringResource(R.string.tingkat_kerusakan),
            description = stringResource(R.string.dummy_level_of_damage)
        )

        Spacer(Modifier.height(24.dp))

        SecondaryDescription(
            title = stringResource(R.string.tingkat_serangan_penyakit),
            description = stringResource(R.string.dummy_attack_rate)
        )
    }
}

@Preview
@Composable
private fun PriceAnalysisContentPreview() {
    KamekAppTheme {
        PriceAnalysisContent()
    }
}