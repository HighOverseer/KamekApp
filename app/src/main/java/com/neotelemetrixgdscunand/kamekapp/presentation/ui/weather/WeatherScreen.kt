package com.neotelemetrixgdscunand.kamekapp.presentation.ui.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.model.WeatherForecastItemDui
import com.neotelemetrixgdscunand.kamekapp.presentation.model.WeatherForecastOverviewDui
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Black10
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey90
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon45
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon53
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Pink
import com.neotelemetrixgdscunand.kamekapp.presentation.util.ImagePainterStable
import com.neotelemetrixgdscunand.kamekapp.presentation.util.collectChannelWhenStarted
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    showSnackbar: (String) -> Unit = {},
    viewModel: WeatherViewModel = hiltViewModel()
){
    val lifecycle = LocalLifecycleOwner.current
    val context = LocalContext.current
    LaunchedEffect(true){
        lifecycle.collectChannelWhenStarted(viewModel.onMessageEvent){
            showSnackbar(it.getValue(context))
        }
    }

    val weatherForecastOverviewDui by viewModel.weatherForecastOverview.collectAsStateWithLifecycle()
    val weatherForecastDataForSeveralDays by viewModel.weatherForecastForSeveralDays.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    WeatherContent(
        modifier = modifier,
        navigateUp = navigateUp,
        weatherForecastOverview = weatherForecastOverviewDui,
        weatherForecastDataForSeveralDays = weatherForecastDataForSeveralDays,
        isLoadingProvider = { isLoading }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherContent(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    weatherForecastOverview: WeatherForecastOverviewDui? = null,
    weatherForecastDataForSeveralDays: ImmutableList<WeatherForecastItemDui> = persistentListOf(),
    isLoadingProvider: () -> Boolean = { false }
) {

    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        containerColor = Grey90,
        modifier = modifier
            .nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Grey90,
                    scrolledContainerColor = Grey90
                ),
                scrollBehavior = scrollBehaviour,
                actions = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp, bottom = 12.dp),
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
                        ) {
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
            )
        }
    ) { innerPadding ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            WeatherScreenBody(
                weatherForecastOverview = weatherForecastOverview,
                weatherForecastDataForSeveralDays = weatherForecastDataForSeveralDays,
                isLoadingProvider = isLoadingProvider
            )
        }
    }
}

@Composable
fun WeatherScreenBody(
    modifier: Modifier = Modifier,
    weatherForecastOverview: WeatherForecastOverviewDui? = null,
    weatherForecastDataForSeveralDays: ImmutableList<WeatherForecastItemDui> = persistentListOf(),
    isLoadingProvider: () -> Boolean = { false }
) {
    val contentItemModifier = remember {
        Modifier
            .padding(horizontal = 16.dp)
    }

    val weatherItemModifier = remember {
        Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
    }
    val scrollState = rememberScrollState()

    Spacer(Modifier.height(12.dp))

    Column(
        modifier = modifier
            .verticalScroll(scrollState)

    ) {
        Text(
            modifier = contentItemModifier,
            text = stringResource(R.string.hari_ini),
            style = MaterialTheme.typography.headlineSmall,
            color = Black10
        )

        Spacer(Modifier.height(16.dp))

        CardWeatherOverview(
            modifier = contentItemModifier,
            weatherForecastOverview = weatherForecastOverview,
            isLoadingProvider = isLoadingProvider
        )

        Spacer(Modifier.height(16.dp))

        Text(
            modifier = contentItemModifier,
            text = stringResource(R.string._10_hari_yang_akan_datang),
            style = MaterialTheme.typography.headlineSmall,
            color = Black10
        )

        Spacer(Modifier.height(16.dp))

        if (isLoadingProvider()){
            repeat(6){
                key(it) {
                    WeatherPredictionItemLoading(
                        modifier = weatherItemModifier
                    )
                }
            }
        }else{
            weatherForecastDataForSeveralDays.forEach {
                key(it.hashCode()) {
                    WeatherPredictionItem(
                        modifier = weatherItemModifier,
                        weatherForecastItem = it
                    )
                }
            }
        }


        Spacer(Modifier.height(48.dp))
    }
}



@Preview(showBackground = true)
@Composable
private fun WeatherScreenPreview() {
    KamekAppTheme {
        WeatherContent()
    }
}