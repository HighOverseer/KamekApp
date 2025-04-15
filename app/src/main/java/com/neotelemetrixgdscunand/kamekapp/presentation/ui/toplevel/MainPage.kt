package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

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
    navigateToTakePhoto: () -> Unit = {}
) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val coroutineScope = rememberCoroutineScope()
    val showSnackbar:(String) -> Unit = remember {
        { message:String  ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
            }
        }
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val shouldShowTopAppBar by state.shouldShowTopAppBar
    val scaffoldModifier = remember(shouldShowTopAppBar) {
        if(shouldShowTopAppBar) {
            modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        } else modifier
    }

    Scaffold(
        modifier = scaffoldModifier,
        containerColor = Grey90,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    Text(data.visuals.message)
                }
            )
        },
        topBar = {
            state.HandleTopAppBarInitialExpandedHeightEffect(
                scrollBehaviorProvider = { scrollBehavior }
            )

            AnimatedVisibility(shouldShowTopAppBar) {
                DiagnosisTopAppBar(
                    scrollBehaviourProvider = { scrollBehavior },
                    onClick = navigateToTakePhoto
                )
            }
        },
        bottomBar = {
            val navigationBarItems = state.navigationBarItems
            val currentTopLevelRoute by state.currentSelectedTopLevelRoute
            val isInTopLevel = currentTopLevelRoute != null

            if (isInTopLevel) {
                BottomNavigationBar(
                    navigationBarItems = navigationBarItems,
                    currentSelectedRoute = currentTopLevelRoute,
                    onSelectedNavigation = { nextDestination ->
                        state.navigateToTopLevel(
                            nextDestination,
                            currentTopLevelRoute
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
                    expandTopAppBar = {
                        scrollBehavior.state.heightOffset = 0f
                    },
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
