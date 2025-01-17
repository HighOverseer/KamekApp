package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme

@Composable
fun ExplorationSection(
    modifier: Modifier = Modifier,
    navigateToNews:()->Unit = {},
    navigateToShop:()->Unit = {},
    navigateToWeather:()->Unit = {},
    showSnackbar:(String)->Unit = {}
) {

    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Text(
            stringResource(R.string.eksplorasi),
            style = MaterialTheme.typography.headlineSmall,
            color = Black10
        )

        Spacer(Modifier.height(16.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ExplorationMenu(
                iconResId = R.drawable.ic_weather_menu,
                label = stringResource(R.string.cuaca),
                onClick = navigateToWeather
            )
            ExplorationMenu(
                iconResId = R.drawable.ic_news_menu,
                label = stringResource(R.string.berita),
                onClick = navigateToNews
            )
            ExplorationMenu(
                iconResId = R.drawable.ic_shop_menu,
                label = stringResource(R.string.toko),
                onClick = navigateToShop
            )

            val message = stringResource(R.string.fitur_belum_tersedia)
            ExplorationMenu(
                iconResId = R.drawable.ic_plant_menu,
                label = stringResource(R.string.tanaman),
                onClick = {
                    showSnackbar(message)
                }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun ExplorationSectionPreview() {
    KamekAppTheme {
        ExplorationSection()
    }
}