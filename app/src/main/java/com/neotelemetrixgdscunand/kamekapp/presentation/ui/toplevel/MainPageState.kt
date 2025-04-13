package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.Navigation
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.util.NavigationBarItem

@Composable
fun rememberMainPageState(
    navHostController: NavHostController = rememberNavController()
): MainPageState {
    return remember(
        navHostController
    ) {
        MainPageState(
            navHostController
        )
    }
}

@Stable
class MainPageState(
    private val navHostController: NavHostController,
) {

    val navigationBarItems: List<NavigationBarItem>
        @Composable get() = remember { getNavigationBarItems() }

    val currentRoute: State<String?>
        @Composable get() {
            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            return remember {
                derivedStateOf {
                    navBackStackEntry?.destination?.route
                }
            }
        }

    val isInTopLevel: State<Boolean>
        @Composable get() {
            val currentRoute by currentRoute
            return remember {
                derivedStateOf {
                    isInTopLevelPage(currentRoute)
                }
            }
        }

    private fun isInTopLevelPage(currentRoute: String?): Boolean {
        return currentRoute == Navigation.Main.Home.stringVal
                || currentRoute == Navigation.Main.Diagnosis.stringVal
                || currentRoute == Navigation.Main.Account.stringVal
    }

    fun navigateToTopLevel(
        nextDestination: Navigation.Main.MainRoute,
        currentRoute: String?
    ) {
        if (currentRoute != nextDestination::class.java.canonicalName) {
            navHostController.navigate(
                nextDestination
            ) {
                // To have a fresh top level destination instance when navigating
                popUpTo(navHostController.graph.startDestinationId) {
                    inclusive = nextDestination == Navigation.Main.Home
                    saveState = false

                }
                launchSingleTop = true
                restoreState = false
            }
        }
    }

    private fun getNavigationBarItems(): List<NavigationBarItem> {
        return listOf(
            NavigationBarItem(
                titleRestId = R.string.beranda,
                iconResId = R.drawable.ic_home,
                route = Navigation.Main.Home
            ),
            NavigationBarItem(
                titleRestId = R.string.diagnosis,
                iconResId = R.drawable.ic_camera,
                route = Navigation.Main.Diagnosis
            ),
            NavigationBarItem(
                titleRestId = R.string.akun,
                iconResId = R.drawable.ic_person,
                route = Navigation.Main.Account
            ),
        )
    }
}