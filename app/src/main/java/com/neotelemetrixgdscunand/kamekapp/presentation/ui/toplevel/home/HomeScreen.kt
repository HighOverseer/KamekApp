package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.home

import android.content.Context
import android.content.res.Configuration
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.common.api.ResolvableApiException
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.domain.model.DiagnosisSessionPreview
import com.neotelemetrixgdscunand.kamekapp.presentation.model.WeatherForecastOverviewDui
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.home.component.ExplorationSection
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.home.component.HomeDiagnosisHistory
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.home.component.HomeHeaderSection
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.home.component.PriceInfoSection
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.home.component.SectionHeadline
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.home.component.WeeklyNews
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.home.component.WeeklyNewsItem
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.home.component.getDummyWeeklyNewsItems
import com.neotelemetrixgdscunand.kamekapp.presentation.util.collectChannelWhenStarted
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    isLocationPermissionGrantedProvider: () -> Boolean? = { false },
    checkLocationPermission: (Context, ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>) -> Unit = { _, _ -> },
    rememberLocationPermissionRequest: @Composable () -> ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>> = { rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) { }
    },
    rememberLocationSettingResolutionLauncher: @Composable () -> ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult> = { rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) {} },
    navigateToNews: () -> Unit = {},
    navigateToShop: () -> Unit = {},
    navigateToWeather: () -> Unit = {},
    navigateToNewsDetail: () -> Unit = {},
    navigateToDiagnosisResult: (Int) -> Unit = { _ -> },
    navigateToNotification: () -> Unit = {},
    showSnackbar: (String) -> Unit = {}
) {

    val lifecycle = LocalLifecycleOwner.current
    val context = LocalContext.current

    val locationPermissionRequest = rememberLocationPermissionRequest()
    val isLocationPermissionGranted = isLocationPermissionGrantedProvider()
    val locationPermissionDeniedMessage = stringResource(R.string.fitur_prediksi_cuaca_tidak_bisa)

    LaunchedEffect(isLocationPermissionGranted) {
        if(isLocationPermissionGranted == null){
            checkLocationPermission(
                context,
                locationPermissionRequest
            )
        }else if(isLocationPermissionGranted == true){
            viewModel.startLocationUpdates()
        }else{
            showSnackbar(locationPermissionDeniedMessage)
            viewModel.stopLocationUpdates()
        }
    }

    val locationSettingResolutionLauncher = rememberLocationSettingResolutionLauncher()
    val locationSettingResolvableErrorMessage = stringResource(R.string.maaf_sepertinya_anda_perlu_mengaktifkan_beberapa_pengaturan_lokasi)
    LaunchedEffect(true) {
        lifecycle.collectChannelWhenStarted(viewModel.uiEvent) {
            when(it){
                is HomeUIEvent.OnFailedFetchWeatherForecast -> {
                    showSnackbar(it.errorUIText.getValue(context))
                }
                is HomeUIEvent.OnLocationResolvableError -> {
                    showSnackbar(locationSettingResolvableErrorMessage)

                    if(it.exception is ResolvableApiException){
                        locationSettingResolutionLauncher.launch(
                            IntentSenderRequest.Builder(it.exception.resolution).build()
                        )
                    }
                }
                is HomeUIEvent.OnLocationUnknownError -> {
                    showSnackbar(it.errorUIText.getValue(context))
                }
            }

        }
    }


    val diagnosisSessionPreviews by viewModel.diagnosisHistory.collectAsStateWithLifecycle()
    val weatherForecastOverview by viewModel.weatherForecastOverview.collectAsStateWithLifecycle()
    val currentLocation by viewModel.currentLocation.collectAsStateWithLifecycle()

    HomeContent(
        modifier = modifier,
        navigateToNews = navigateToNews,
        navigateToShop = navigateToShop,
        navigateToWeather = navigateToWeather,
        navigateToNewsDetail = navigateToNewsDetail,
        diagnosisSessionPreviews = diagnosisSessionPreviews,
        navigateToDiagnosisResult = navigateToDiagnosisResult,
        navigateToNotification = navigateToNotification,
        showSnackbar = showSnackbar,
        weatherForecastOverview = weatherForecastOverview,
        currentLocationNameProvider = { currentLocation?.name }
    )
}


@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    weatherForecastOverview: WeatherForecastOverviewDui? = null,
    currentLocationNameProvider: () -> String? = { null },
    navigateToNews: () -> Unit = {},
    navigateToShop: () -> Unit = {},
    navigateToWeather: () -> Unit = {},
    navigateToNewsDetail: () -> Unit = {},
    diagnosisSessionPreviews: ImmutableList<DiagnosisSessionPreview> = persistentListOf(),
    navigateToDiagnosisResult: (Int) -> Unit = { _ -> },
    navigateToNotification: () -> Unit = {},
    showSnackbar: (String) -> Unit = {}
) {


    val weeklyNewsItems: ImmutableList<WeeklyNewsItem> = remember {
        getDummyWeeklyNewsItems().toImmutableList()
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        HomeHeaderSection(
            modifier = Modifier.fillMaxWidth(),
            navigateToNotification = navigateToNotification,
            weatherForecastOverview = weatherForecastOverview,
            currentLocationProvider = currentLocationNameProvider
        )

        PriceInfoSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
        )

        ExplorationSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            navigateToNews = navigateToNews,
            navigateToShop = navigateToShop,
            navigateToWeather = navigateToWeather,
            showSnackbar = showSnackbar
        )

        Spacer(Modifier.height(24.dp))

        SectionHeadline(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp),
            leadingIconResId = R.drawable.ic_camera_gradient,
            trailingIconResId = R.drawable.ic_right_arrow,
            title = stringResource(R.string.riwayat_diagnosis)
        )

        Spacer(
            Modifier.height(16.dp)
        )

        val diagnosisHistoryListState = rememberLazyListState()

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            state = diagnosisHistoryListState
        ) {
            items(diagnosisSessionPreviews, { it.id }) { item ->
                HomeDiagnosisHistory(
                    modifier = Modifier
                        .clickable {
                            navigateToDiagnosisResult(item.id)
                        },
                    item = item
                )
            }
        }

        Spacer(
            Modifier.height(24.dp)
        )

        SectionHeadline(
            modifier = Modifier.padding(horizontal = 16.dp),
            title = stringResource(R.string.berita_mingguan_kamek),
            leadingIconResId = R.drawable.ic_news_gradient
        )

        val configuration = LocalConfiguration.current
        val lazyColumnMaxHeight = remember {
            val screenHeightDp = configuration.screenHeightDp.dp
            val multiplier = when (configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> 2
                else -> 5
            }
            screenHeightDp * multiplier
        }

        val weeklyItemModifier = remember {
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .clickable(onClick = navigateToNewsDetail)
        }

        val lazyListState = rememberLazyListState()
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = lazyColumnMaxHeight),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(weeklyNewsItems, { it.id }) { item ->
                WeeklyNews(
                    modifier = weeklyItemModifier,
                    item = item
                )
            }
        }

//        weeklyNewsItems.forEach {
//            key(it.id) {
//                WeeklyNews(
//                    modifier = weeklyItemModifier,
//                    item = it
//                )
//            }
//        }
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    KamekAppTheme {
        HomeContent()
    }
}
