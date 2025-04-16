package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.Navigation
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.util.NavigationBarItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberMainPageState(
    navHostController: NavHostController = rememberNavController(),
    topAppBarState: TopAppBarState = rememberTopAppBarState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): MainPageState {
    return remember(
        navHostController,
        topAppBarState,
        coroutineScope
    ) {
        MainPageState(
            navHostController = navHostController,
            topAppBarState = topAppBarState,
            coroutineScope = coroutineScope
        )
    }
}

@Stable
@OptIn(ExperimentalMaterial3Api::class)
class MainPageState(
    private val navHostController: NavHostController,
    private val topAppBarState: TopAppBarState,
    private val coroutineScope: CoroutineScope
) {

    val navigationBarItems: ImmutableList<NavigationBarItem>
        @Composable get() = remember { getNavigationBarItems() }

    private val topLevelRoutesStringValMap = mapOf(
        Navigation.Main.Home.stringVal to Navigation.Main.Home,
        Navigation.Main.Diagnosis.stringVal to Navigation.Main.Diagnosis,
        Navigation.Main.Account.stringVal to Navigation.Main.Account,
    )

    val currentSelectedAndNavigatedTopLevelRoute: State<Navigation.Main.MainRoute?>
        @Composable get() {
            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            return remember {
                derivedStateOf {
                    val currentRoute = navBackStackEntry?.destination?.route
                    topLevelRoutesStringValMap[currentRoute]
                }
            }
        }

    var currentJustSelectedTopLevelRoute by mutableStateOf<Navigation.Main.MainRoute?>(null)
        private set

    val exitUntilCollapsedScrollBehavior: TopAppBarScrollBehavior
        @Composable get() {
            val shouldShowTopAppBar by shouldShowTopAppBar
            return TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
                topAppBarState,
                canScroll = { shouldShowTopAppBar }
            )
        }

    private val shouldShowTopAppBar: State<Boolean>
        @Composable get() {
            val currentRoute by currentSelectedAndNavigatedTopLevelRoute
            return remember {
                derivedStateOf {
                    currentRoute == Navigation.Main.Diagnosis
                }
            }
        }

    var isTopBarShown by mutableStateOf(false)
        private set

    fun expandTopAppBar() {
        topAppBarState.heightOffset = 0f
    }

    @Composable
    fun HandleTopAppBarInitialExpandedHeightEffect() {
        val shouldShowTopAppBar by shouldShowTopAppBar
        LaunchedEffect(shouldShowTopAppBar) {
            if (shouldShowTopAppBar) {
                //Make sure Top App Bar Expanded Before AnimationVisibility start
                expandTopAppBar()
                isTopBarShown = true
            }
        }
    }

    private var navigationJob: Job? = null
    fun navigateToTopLevel(
        nextDestination: Navigation.Main.MainRoute,
        currentSelectedTopLevelRoute: Navigation.Main.MainRoute? = Navigation.Main.Home
    ) {
        if (currentSelectedTopLevelRoute == nextDestination) return

        if (navigationJob != null) return

        navigationJob = coroutineScope.launch {
            this@MainPageState.currentJustSelectedTopLevelRoute = nextDestination

            val isExitingDiagnosisRoute = currentSelectedTopLevelRoute == Navigation.Main.Diagnosis
            if (isExitingDiagnosisRoute) {
                isTopBarShown = false
                delay(TopAppBarVisibilityAnimationDurationMillis.toLong())
            }

            navHostController.navigate(
                nextDestination,
            ) {
                // To have a fresh top level destination instance when navigating in top level
                popUpTo(navHostController.graph.startDestinationId) {
                    //inclusive = nextDestination == Navigation.Main.Home
                    saveState = true

                }
                launchSingleTop = true
                restoreState = true
            }
        }.apply { invokeOnCompletion { navigationJob = null } }
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

    companion object {
        const val TopAppBarVisibilityAnimationDurationMillis = 200
    }
}