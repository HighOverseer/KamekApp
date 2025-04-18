package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel

import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
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

    val shouldShowTopAppBar by state.shouldShowTopAppBar.collectAsStateWithLifecycle()
    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = topAppBarState,
        canScroll = { shouldShowTopAppBar }
    )
    val expandTopAppBar = remember { {
            topAppBarState.heightOffset = 0f
        }
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val coroutineScope = rememberCoroutineScope()
    val showSnackbar:(String) -> Unit = remember { {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(it)
            }
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
                shouldShowTopAppBar = shouldShowTopAppBar,
                expandTopAppBar = expandTopAppBar
            )

            DiagnosisTopAppBar(
                isVisibleProvider = { state.isTopBarShown },
                scrollBehavior = scrollBehavior,
                onClick = navigateToTakePhoto,
                visibilityAnimationDurationMillis = MainPageState
                    .TOP_APP_BAR_VISIBILITY_ANIMATION_DURATION_MILLIS
            )
        },
        bottomBar = {
            val navigationBarItems = state.bottomNavigationBarItems
            val isInTopLevel by state.isInTopLevel.collectAsStateWithLifecycle()
            if(isInTopLevel){
                BottomNavigationBar(
                    navigationBarItems = navigationBarItems,
                    selectedNavigationProvider = { state.selectedBottomNavigationRoute },
                    onSelectedNavigation = { selectedNavigation ->
                        state.navigateToTopLevel(
                            selectedNavigation
                        )
                    }
                )
            }

        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {

            NavHost(
                navController = mainNavHostController,
                startDestination = Navigation.Main.Home,
            ) {
                composable<Navigation.Main.Home>{
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
                        expandTopAppBar = expandTopAppBar,
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
