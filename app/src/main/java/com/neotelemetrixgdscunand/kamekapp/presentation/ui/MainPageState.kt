package com.neotelemetrixgdscunand.kamekapp.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.util.NavigationBarItem

@Composable
fun rememberMainPageState(
    navHostController: NavHostController = rememberNavController(),
    showStatusBar: () -> Unit = { },
    hideStatusBar: () -> Unit = { }
):MainPageState{
    return remember(
        navHostController,
        showStatusBar,
        hideStatusBar
    ) {
        MainPageState(
            navHostController,
            showStatusBar,
            hideStatusBar
        )
    }
}

@Stable
class MainPageState(
    private val navHostController: NavHostController,
    private val showStatusBar:() -> Unit,
    private val hideStatusBar:() -> Unit
){

    val navigationBarItems:List<NavigationBarItem>
        @Composable get() = remember { getNavigationBarItems() }

    val currentRoute:State<String?>
        @Composable get() {
            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            return remember{
                derivedStateOf {
                    navBackStackEntry?.destination?.route
                }
            }
        }

    val isInTopLevel:State<Boolean>
        @Composable get(){
            val currentRoute by currentRoute
            return remember{
                derivedStateOf {
                    isInTopLevelPage(currentRoute)
                }
            }
        }


    private var isLastDestinationFromTakePictureScreen = false
    @Composable
    fun HandleStatusBarEffect() {
        val currentRoute by currentRoute
        LaunchedEffect(currentRoute) {
            if (currentRoute == Navigation.Main.TakePhoto::class.java.canonicalName) {
                hideStatusBar()
                isLastDestinationFromTakePictureScreen = true
            } else if (isLastDestinationFromTakePictureScreen) {
                showStatusBar()
                isLastDestinationFromTakePictureScreen = false
            }
        }
    }

    private fun isInTopLevelPage(currentRoute: String?): Boolean {
        return currentRoute == Navigation.Main.TopLevel.Home.canonicalName
                || currentRoute == Navigation.Main.TopLevel.Diagnosis.canonicalName
                || currentRoute == Navigation.Main.TopLevel.Account.canonicalName
    }

    fun navigateToTopLevel(
        nextDestination: Navigation.Main.TopLevel,
        currentRoute: String?
    ){
        if (currentRoute != nextDestination::class.java.canonicalName) {
            navHostController.navigate(
                nextDestination
            ) {
                popUpTo(navHostController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    private fun getNavigationBarItems(): List<NavigationBarItem> {
        return listOf(
            NavigationBarItem(
                titleRestId = R.string.beranda,
                iconResId = R.drawable.ic_home,
                route = Navigation.Main.TopLevel.Home
            ),
            NavigationBarItem(
                titleRestId = R.string.diagnosis,
                iconResId = R.drawable.ic_camera,
                route = Navigation.Main.TopLevel.Diagnosis
            ),
            NavigationBarItem(
                titleRestId = R.string.akun,
                iconResId = R.drawable.ic_person,
                route = Navigation.Main.TopLevel.Account
            ),
        )
    }
}