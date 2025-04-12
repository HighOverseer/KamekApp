package com.neotelemetrixgdscunand.kamekapp.presentation.ui

import android.content.Context
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey90
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.cacaoimagedetail.CacaoImageDetailScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.DiagnosisResultScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.news.NewsDetailScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.news.NewsScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.notif.screen.CacaoRequestScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.notif.screen.NotificationScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.shop.ShopScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.TakePhotoScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.BottomNavigationBar
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.account.AccountScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.diagnosishistory.DiagnosisScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.home.HomeScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.weather.WeatherScreen
import kotlinx.coroutines.launch

@Composable
fun MainPage(
    modifier: Modifier = Modifier,
    state: MainPageState = rememberMainPageState(),
    mainNavHostController: NavHostController = rememberNavController(),
    provideIsCameraPermissionGranted: () -> Boolean? = { true },
    checkCameraPermission: (Context, ManagedActivityResultLauncher<String, Boolean>) -> Unit = { _, _ -> },
    rememberCameraPermissionRequest: @Composable () -> ManagedActivityResultLauncher<String, Boolean>
) {

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val coroutineScope = rememberCoroutineScope()
    val currentRoute by state.currentRoute

    state.HandleStatusBarEffect()

    Scaffold(
        containerColor = Grey90,
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    Text(data.visuals.message)
                }
            )
        },
        bottomBar = {
            val isInTopLevel by state.isInTopLevel
            val navigationBarItems = state.navigationBarItems

            if (isInTopLevel) {
                BottomNavigationBar(
                    navigationBarItems = navigationBarItems,
                    currentRoute = currentRoute,
                    onSelectedNavigation = { nextDestination ->
                        state.navigateToTopLevel(nextDestination, currentRoute)
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = mainNavHostController,
            startDestination = Navigation.Main.TopLevel.Home,
        ) {
            composable<Navigation.Main.TopLevel.Home> {
                HomeScreen(
                    navigateToNews = {
                        mainNavHostController.navigate(
                            Navigation.Main.News
                        )
                    },
                    navigateToShop = {
                        mainNavHostController.navigate(
                            Navigation.Main.Shop
                        )
                    },
                    navigateToWeather = {
                        mainNavHostController.navigate(
                            Navigation.Main.Weather
                        )
                    },
                    navigateToNewsDetail = {
                        mainNavHostController.navigate(
                            Navigation.Main.NewsDetail
                        )
                    },
                    navigateToDiagnosisResult = { sessionId ->
                        mainNavHostController.navigate(
                            Navigation.Main.DiagnosisResult(
                                sessionId = sessionId,
                                newSessionName = null,
                                newUnsavedSessionImagePath = null
                            )
                        )
                    },
                    navigateToNotification = {
                        mainNavHostController.navigate(
                            Navigation.Main.Notification
                        )
                    },
                    showSnackbar = { message ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(message = message)
                        }
                    }
                )
            }
            composable<Navigation.Main.TopLevel.Diagnosis> {
                DiagnosisScreen(
                    navigateToTakePhoto = {
                        mainNavHostController.navigate(
                            Navigation.Main.TakePhoto
                        )
                    },
                    navigateToDiagnosisResult = { sessionId ->
                        mainNavHostController.navigate(
                            Navigation.Main.DiagnosisResult(
                                sessionId = sessionId,
                                newSessionName = null,
                                newUnsavedSessionImagePath = null
                            )
                        )
                    }
                )
            }

            composable<Navigation.Main.TopLevel.Account> {
                AccountScreen()
            }

            composable<Navigation.Main.TakePhoto> {
                TakePhotoScreen(
                    provideIsCameraPermissionGranted = provideIsCameraPermissionGranted,
                    showSnackBar = { message ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = message,
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                    navigateUp = mainNavHostController::navigateUp,
                    navigateToResult = { newSessionName, newUnsavedSessionImagePath ->
                        mainNavHostController.navigate(
                            Navigation.Main.DiagnosisResult(
                                sessionId = null,
                                newSessionName = newSessionName,
                                newUnsavedSessionImagePath = newUnsavedSessionImagePath
                            )
                        ) {
                            popUpTo<Navigation.Main.TopLevel.Diagnosis> {
                                inclusive = false
                            }
                        }
                    },
                    checkCameraPermission = checkCameraPermission,
                    rememberCameraPermissionRequest = rememberCameraPermissionRequest
                )
            }

            composable<Navigation.Main.DiagnosisResult> {
                DiagnosisResultScreen(
                    navigateUp = mainNavHostController::navigateUp,
                    showSnackbar = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(it)
                        }
                    },
                    navigateToCacaoImageDetail = { sessionId, detectedCacaoId, imagePreviewPath ->
                        mainNavHostController.navigate(
                            Navigation.Main.CacaoImageDetail(
                                diagnosisSessionId = sessionId,
                                detectedCacaoId = detectedCacaoId,
                                imagePath = imagePreviewPath
                            )
                        )
                    }
                )
            }

            composable<Navigation.Main.News> {
                NewsScreen(
                    navigateUp = mainNavHostController::navigateUp,
                    navigateToDetail = {
                        mainNavHostController.navigate(Navigation.Main.NewsDetail)
                    }
                )
            }

            composable<Navigation.Main.NewsDetail> {
                NewsDetailScreen(
                    navigateUp = mainNavHostController::navigateUp
                )
            }

            composable<Navigation.Main.Weather> {
                WeatherScreen(
                    navigateUp = mainNavHostController::navigateUp
                )
            }

            composable<Navigation.Main.Shop> {
                ShopScreen(
                    navigateUp = mainNavHostController::navigateUp
                )
            }

            composable<Navigation.Main.Notification> {
                NotificationScreen(
                    navigateUp = mainNavHostController::navigateUp,
                    navigateToCacaoRequestScreen = {
                        mainNavHostController.navigate(
                            Navigation.Main.CacaoRequest
                        )
                    }
                )
            }

            composable<Navigation.Main.CacaoRequest> {
                CacaoRequestScreen(
                    navigateUp = mainNavHostController::navigateUp
                )
            }

            composable<Navigation.Main.CacaoImageDetail> {
                CacaoImageDetailScreen(
                    navigateUp = mainNavHostController::navigateUp
                )
            }
        }
    }
}

