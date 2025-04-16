package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
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

@OptIn(ExperimentalMaterial3Api::class)
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
    navigateToTakePhoto: () -> Unit = {},
    showSnackbar: (String) -> Unit = {}
) {

    val scrollBehavior = state.exitUntilCollapsedScrollBehavior

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = Grey90,
        topBar = {
            state.HandleTopAppBarInitialExpandedHeightEffect()

            DiagnosisTopAppBar(
                isVisibleProvider = { state.isTopBarShown },
                scrollBehavior = scrollBehavior,
                onClick = navigateToTakePhoto,
                visibilityAnimationDurationMillis = MainPageState
                    .TopAppBarVisibilityAnimationDurationMillis
            )

        },
        bottomBar = {
            val currentSelectedAndNavigateTopLevelRoute by state.currentSelectedAndNavigatedTopLevelRoute
            val navigationBarItems = state.navigationBarItems
            val isInTopLevel = currentSelectedAndNavigateTopLevelRoute != null

            if (isInTopLevel) {
                BottomNavigationBar(
                    navigationBarItems = navigationBarItems,
                    selectedNavigationProvider = {
                        state.currentJustSelectedTopLevelRoute ?: currentSelectedAndNavigateTopLevelRoute
                    },
                    onSelectedNavigation = { selectedNavigation ->
                        state.navigateToTopLevel(
                            selectedNavigation,
                            currentSelectedAndNavigateTopLevelRoute
                        )
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
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
                    expandTopAppBar = state::expandTopAppBar,
                )
            }

            composable<Navigation.Main.Account> {
                AccountScreen()
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
