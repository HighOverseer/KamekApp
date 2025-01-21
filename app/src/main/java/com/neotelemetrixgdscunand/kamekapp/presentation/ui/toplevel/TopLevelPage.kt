package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.neotelemetrixgdscunand.kamekapp.MainActivity
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey90
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.BottomBarRoute
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.CacaoRequestRoute
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.DiagnosisResultRoute
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.News
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.NewsDetail
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.Notification
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.Shop
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.TakePhotoRoute
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.Weather
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.DiagnosisResultScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.news.NewsDetailScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.news.NewsScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.notif.screen.CacaoRequestScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.notif.screen.NotificationScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.shop.ShopScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.TakePhotoScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.BottomNavigationBar
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.getBottomBarItems
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.screen.AccountScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.screen.DiagnosisScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.screen.HomeScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.util.isInTopLevelPage
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.weather.WeatherScreen
import kotlinx.coroutines.launch

@Composable
fun TopLevelPage(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController(),
    isCameraPermissionGranted:Boolean?,
) {

    val navigationBarItems = remember {
        getBottomBarItems()
    }

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()

    val currentRoute by rememberCurrentRoute(navBackStackEntry)

    val isInTopLevelRoute = remember(currentRoute) {
        isInTopLevelPage(currentRoute)
    }
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    var isLastDestinationFromTakePictureScreen by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(currentRoute) {
        if(context !is MainActivity) return@LaunchedEffect

        if(currentRoute == TakePhotoRoute::class.java.canonicalName){
            context.hideStatusBar()
            isLastDestinationFromTakePictureScreen = true
        }else if(isLastDestinationFromTakePictureScreen){
            context.showStatusBar()
            isLastDestinationFromTakePictureScreen = false
        }
    }

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
            if(isInTopLevelRoute){
                BottomNavigationBar(
                    navigationBarItems = navigationBarItems,
                    currentRoute = currentRoute,
                    onSelectedNavigation = { nextDestination ->
                        if(currentRoute != nextDestination::class.java.canonicalName){
                            navHostController.navigate(
                                nextDestination
                            ){
                                popUpTo(navHostController.graph.startDestinationId){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }

        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navHostController,
            startDestination = BottomBarRoute.Home,
        ) {
            composable<BottomBarRoute.Home> {
                HomeScreen(
                    navigateToNews = {
                        navHostController.navigate(
                            News
                        )
                    },
                    navigateToShop = {
                        navHostController.navigate(
                            Shop
                        )
                    },
                    navigateToWeather = {
                        navHostController.navigate(
                            Weather
                        )
                    },
                    navigateToNewsDetail = {
                        navHostController.navigate(
                            NewsDetail
                        )
                    },
                    navigateToDiagnosisResult = { outputId, imageUrlOrPath ->
                        navHostController.navigate(
                            DiagnosisResultRoute(imageUrlOrPath, outputId)
                        )
                    },
                    navigateToNotification = {
                        navHostController.navigate(
                            Notification
                        )
                    },
                    showSnackbar = { message ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(message = message)
                        }
                    }
                )
            }
            composable<BottomBarRoute.Diagnosis> {
                DiagnosisScreen(
                    navigateToTakePhoto = {
                        navHostController.navigate(
                            TakePhotoRoute
                        )
                    },
                    navigateToDiagnosisResult = { outputId, imagePath ->
                        navHostController.navigate(
                            DiagnosisResultRoute(
                                imagePath = imagePath,
                                outputId = outputId
                            )
                        )
                    }
                )
            }

            composable<BottomBarRoute.Account> {
                AccountScreen()
            }

            composable<TakePhotoRoute> {
                TakePhotoScreen(
                    isCameraPermissionGranted = isCameraPermissionGranted,
                    showSnackBar = { message ->
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = message,
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                    navigateUp = navHostController::navigateUp,
                    navigateToResult = { imagePath ->
                        navHostController.navigate(DiagnosisResultRoute(imagePath, null)){
                            popUpTo<BottomBarRoute.Diagnosis>{
                                inclusive = false
                            }
                        }
                    }
                )
            }

            composable<DiagnosisResultRoute> {
                val route = it.toRoute<DiagnosisResultRoute>()
                DiagnosisResultScreen(
                    navigateUp = navHostController::navigateUp,
                    imagePath = route.imagePath,
                    outputId = route.outputId
                )
            }

            composable<News> {
                NewsScreen(
                    navigateUp = navHostController::navigateUp,
                    navigateToDetail = {
                        navHostController.navigate(NewsDetail)
                    }
                )
            }

            composable<NewsDetail>{
                NewsDetailScreen(
                    navigateUp = navHostController::navigateUp
                )
            }

            composable<Weather>{
                WeatherScreen(
                    navigateUp = navHostController::navigateUp
                )
            }

            composable<Shop> {
                ShopScreen(
                    navigateUp = navHostController::navigateUp
                )
            }

            composable<Notification> {
                NotificationScreen(
                    navigateUp = navHostController::navigateUp,
                    navigateToCacaoRequestScreen = {
                        navHostController.navigate(
                            CacaoRequestRoute
                        )
                    }
                )
            }

            composable<CacaoRequestRoute> {
                CacaoRequestScreen(
                    navigateUp = navHostController::navigateUp
                )
            }
        }
    }
}

@Composable
private fun rememberCurrentRoute(navBackStackEntry: NavBackStackEntry?):State<String?>{
    return remember(navBackStackEntry) {
        derivedStateOf {
            navBackStackEntry?.destination?.route
        }
    }
}




@Preview
@Composable
private fun TopLevelPagePreview() {
    KamekAppTheme {
        TopLevelPage(isCameraPermissionGranted = true)
    }
}
