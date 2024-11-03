package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
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
import com.neotelemetrixgdscunand.kamekapp.MainActivity
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey90
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.BottomBarRoute
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.DiagnosisResultRoute
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.TakePhotoRoute
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.diagnosisresult.DiagnosisResultScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.takephoto.TakePhotoScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.BottomNavigationBar
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.getBottomBarItems
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.screen.AccountScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.screen.DiagnosisScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.screen.HomeScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.util.isInTopLevelPage
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
                HomeScreen()
            }
            composable<BottomBarRoute.Diagnosis> {
                DiagnosisScreen(
                    navigateToTakePhoto = {
                        navHostController.navigate(
                            TakePhotoRoute
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
                    navigateToResult = {
                        navHostController.navigate(DiagnosisResultRoute){
                            popUpTo<BottomBarRoute.Diagnosis>{
                                inclusive = false
                            }
                        }
                    }
                )
            }

            composable<DiagnosisResultRoute> {
                DiagnosisResultScreen(
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
