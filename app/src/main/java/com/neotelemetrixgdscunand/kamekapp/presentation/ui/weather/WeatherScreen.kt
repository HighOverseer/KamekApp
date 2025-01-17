package com.neotelemetrixgdscunand.kamekapp.presentation.ui.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey90
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon45
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon53
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Pink

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {}
) {
    val listState = rememberLazyListState()

    val contentItemModifier = remember {
        Modifier
            .padding(horizontal = 16.dp)
    }

    val cardModifier = remember {
        Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colorStops = arrayOf(
                        Pair(0f, Maroon53),
                        Pair(100f, Maroon45)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 24.dp, horizontal = 16.dp)
    }

    val weatherData = remember {
        getDummyWeatherPredictionItemData()
    }

    val weatherItemModifier = remember {
        Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
    }

    LazyColumn(
        modifier = modifier
            .background(Grey90),
        state = listState,
        contentPadding = PaddingValues(top = 24.dp, bottom = 42.dp)
    ) {

        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                IconButton(
                    modifier = Modifier
                        .align(Alignment.CenterStart),
                    onClick = navigateUp
                ) {
                    Icon(
                        modifier = Modifier
                            .width(21.dp)
                            .height(20.dp),
                        imageVector = ImageVector
                            .vectorResource(R.drawable.ic_arrow_left),
                        contentDescription = null,
                        tint = Black10
                    )
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(
                        imageVector = ImageVector
                            .vectorResource(R.drawable.ic_needle_location),
                        contentDescription = null
                    )

                    Spacer(Modifier.width(8.dp))

                    Text(
                        stringResource(R.string.padang),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.width(8.dp))

                    Icon(
                        imageVector = ImageVector
                            .vectorResource(R.drawable.ic_down_arrow),
                        tint = Black10,
                        contentDescription = null
                    )
                }

            }
        }

        item {
            Spacer(Modifier.height(16.dp))
        }

        item {
            Text(
                modifier = contentItemModifier,
                text = stringResource(R.string.hari_ini),
                style = MaterialTheme.typography.headlineSmall,
                color = Black10
            )

            Spacer(Modifier.height(16.dp))
        }

        item {
            Column(
                modifier = contentItemModifier.then(cardModifier),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column {

                }
                Image(
                    painter = painterResource(R.drawable.ic_weather),
                    contentDescription = stringResource(R.string.gambar_cuaca)
                )
                Text(
                    text = stringResource(R.string._17),
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    stringResource(R.string.hujan_lebat),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Spacer(Modifier.height(8.dp))

                Row {
                    Text(
                        stringResource(R.string.h_24),
                        style = MaterialTheme.typography.titleMedium,
                        color = Pink
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        stringResource(R.string.l_17),
                        style = MaterialTheme.typography.titleMedium,
                        color = Pink
                    )
                }



                Divider(
                    modifier =
                    Modifier.padding(vertical = 16.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            stringResource(R.string.kelembapan),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.W300
                            ),
                            color = Pink
                        )
                        Spacer(Modifier.height(7.dp))
                        Text(
                            stringResource(R.string._87),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                    Column {
                        Text(
                            stringResource(R.string.kec_angin),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.W300
                            ),
                            color = Pink
                        )
                        Spacer(Modifier.height(7.dp))
                        Text(
                            stringResource(R.string._2_km_jam),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                    Column {
                        Text(
                            stringResource(R.string.curah_hujan),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.W300
                            ),
                            color = Pink
                        )
                        Spacer(Modifier.height(7.dp))
                        Text(
                            stringResource(R.string._100mm),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                }
            }
        }

        item {
            Spacer(Modifier.height(27.dp))

            Text(
                modifier = contentItemModifier,
                text = stringResource(R.string._10_hari_yang_akan_datang),
                style = MaterialTheme.typography.headlineSmall,
                color = Black10
            )

            Spacer(Modifier.height(16.dp))
        }

        items(weatherData, key = {it.id}){
            WeatherPredictionItem(
                modifier = weatherItemModifier,
                date = it.date,
                temperatureRange = it.temperatureRange,
                windVelocity = it.windVelocity,
                humidityPercentage = it.humidityPercentage
            )
        }



    }
}

@Preview(showBackground = true)
@Composable
private fun WeatherScreenPreview() {
    KamekAppTheme {
        WeatherScreen()
    }
}