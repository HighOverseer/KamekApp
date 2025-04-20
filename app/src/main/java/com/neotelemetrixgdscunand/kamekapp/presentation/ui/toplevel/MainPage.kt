package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import kotlinx.coroutines.launch

@Composable
fun MainPage(
    modifier: Modifier = Modifier,
    state: MainPageState = rememberMainPageState(),
    mainNavHostController: NavHostController = rememberNavController(),
    navigateToNews: () -> Unit = {},
    navigateToShop: () -> Unit = {},
    navigateToWeather: () -> Unit = {},
    navigateToNewsDetail: () -> Unit = {},
    navigateToDiagnosisResult: (Int) -> Unit = {},
    navigateToNotification: () -> Unit = {},
    navigateToTakePhoto: () -> Unit = {}
) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val coroutineScope = rememberCoroutineScope()
    val showSnackbar: (String) -> Unit = remember {
        {
            coroutineScope.launch {
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
                    Text(data.visuals.message)
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
                    AccountScreen()
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
