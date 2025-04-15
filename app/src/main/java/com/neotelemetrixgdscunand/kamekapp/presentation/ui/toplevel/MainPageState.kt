package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
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
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.Navigation
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.util.NavigationBarItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

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

    val navigationBarItems: ImmutableList<NavigationBarItem>
        @Composable get() = remember { getNavigationBarItems() }

    private val topLevelRoutesStringValMap = mapOf(
        Navigation.Main.Home.stringVal to Navigation.Main.Home,
        Navigation.Main.Diagnosis.stringVal to Navigation.Main.Diagnosis,
        Navigation.Main.Account.stringVal to Navigation.Main.Account,
    )

    val currentSelectedTopLevelRoute:State<Navigation.Main.MainRoute?>
        @Composable get() {
            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            return remember {
                derivedStateOf {
                    val currentRoute = navBackStackEntry?.destination?.route
                    topLevelRoutesStringValMap[currentRoute]
                }
            }
        }

    val shouldShowTopAppBar:State<Boolean>
        @Composable get() {
            val currentRoute by currentSelectedTopLevelRoute
            return remember {
                derivedStateOf {
                    currentRoute == Navigation.Main.Diagnosis
                }
            }
        }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HandleTopAppBarInitialExpandedHeightEffect(
        scrollBehaviorProvider: @Composable () -> TopAppBarScrollBehavior = {
            TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        }
    ) {
        val shouldShowTopAppBar by shouldShowTopAppBar
        val scrollBehavior = scrollBehaviorProvider()
        LaunchedEffect(shouldShowTopAppBar) {
            if(shouldShowTopAppBar){
                //Make sure Top App Bar Expanded Before AnimationVisibility start
                scrollBehavior.state.heightOffset = 0f
            }
        }
    }

    fun navigateToTopLevel(
        nextDestination: Navigation.Main.MainRoute,
        currentSelectedTopLevelRoute: Navigation.Main.MainRoute? = Navigation.Main.Home
    ) {
        if (currentSelectedTopLevelRoute != nextDestination) {
            navHostController.navigate(
                nextDestination
            ) {
                // To have a fresh top level destination instance when navigating in top level
                popUpTo(navHostController.graph.startDestinationId) {
                    inclusive = nextDestination == Navigation.Main.Home
                    saveState = false

                }
                launchSingleTop = true
                restoreState = false
            }
        }
    }

    private fun getNavigationBarItems(): ImmutableList<NavigationBarItem> {
        return persistentListOf(
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