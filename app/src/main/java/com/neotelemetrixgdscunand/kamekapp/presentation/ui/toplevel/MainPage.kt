package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel

import android.content.Context
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey90
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.Navigation
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.account.AccountScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.diagnosishistory.DiagnosisScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.home.HomeScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.utils.MessageSnackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun MainPage(
    modifier: Modifier = Modifier,
    state: MainPageState = rememberMainPageState(),
    mainNavHostController: NavHostController = rememberNavController(),
    navigateToShop: () -> Unit = {},
    navigateToWeather: () -> Unit = {},
    navigateToNewsDetail: (Int) -> Unit = { _ -> },
    navigateToDiagnosisResult: (Int) -> Unit = {},
    navigateToNotification: () -> Unit = {},
    navigateToTakePhoto: () -> Unit = {},
    navigateToProfile: () -> Unit = {},
    navigateToAuth: (String) -> Unit = {},
    navigateToNews: () -> Unit = {},
    isLocationPermissionGrantedProvider: () -> Boolean? = { false },
    checkLocationPermission: (Context, ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>) -> Unit = { _, _ -> },
    rememberLocationPermissionRequest: @Composable () -> ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>> = {
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { }
    },
    rememberLocationSettingResolutionLauncher: @Composable () -> ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult> = {
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) {}
    }
) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    var showingSnackbarJob: Job? = remember {
        null
    }
    val coroutineScope = rememberCoroutineScope()
    val showSnackbar: (String) -> Unit = remember {
        {
            showingSnackbarJob?.cancel()
            showingSnackbarJob = coroutineScope.launch {
                snackbarHostState.showSnackbar(it)
            }
        }
    }

    var bottomBarHeightPx by remember {
        mutableIntStateOf(0)
    }


    Scaffold(
        modifier = modifier,
        containerColor = Grey90,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    MessageSnackbar(
                        message = data.visuals.message,
                        modifier = Modifier
                            .fillMaxHeight(0.08f),
                    )
                }
            )
        },
        bottomBar = {
            val navigationBarItems = state.bottomNavigationBarItemData
            val currentSelectedTopLevelRoute by state.currentSelectedTopLevelRoute.collectAsStateWithLifecycle()

            MeasuredBottomNavigationBar(
                navigationBarItemsData = navigationBarItems,
                selectedNavigationProvider = { currentSelectedTopLevelRoute },
                onSelectedNavigation = { selectedNavigation ->
                    state.navigateToTopLevel(
                        selectedNavigation
                    )
                },
                onBottomNavigationBarHeightMeasured = {
                    bottomBarHeightPx = it
                }
            )

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            NavHost(
                navController = mainNavHostController,
                startDestination = Navigation.Main.Home,
            ) {
                composable<Navigation.Main.Home> {
                    HomeScreen(
                        isLocationPermissionGrantedProvider = isLocationPermissionGrantedProvider,
                        checkLocationPermission = checkLocationPermission,
                        rememberLocationPermissionRequest = rememberLocationPermissionRequest,
                        rememberLocationSettingResolutionLauncher = rememberLocationSettingResolutionLauncher,
                        navigateToNews = navigateToNews,
                        navigateToShop = navigateToShop,
                        navigateToWeather = navigateToWeather,
                        navigateToNewsDetail = navigateToNewsDetail,
                        navigateToDiagnosisResult = navigateToDiagnosisResult,
                        navigateToNotification = navigateToNotification,
                        showSnackbar = showSnackbar
                    )
                }
                composable<Navigation.Main.Diagnosis> {
                    DiagnosisScreen(
                        navigateToDiagnosisResult = navigateToDiagnosisResult,
                        navigateToTakePhoto = navigateToTakePhoto,
                        bottomBarHeightPxProvider = { bottomBarHeightPx }
                    )
                }

                composable<Navigation.Main.Account> {
                    AccountScreen(
                        navigateToProfile = navigateToProfile,
                        navigateToAuth = navigateToAuth,
                    )
                }

            }

        }

    }
}

@Preview
@Composable
private fun MainPagePreview() {
    KamekAppTheme {
        MainPage()
    }
}
