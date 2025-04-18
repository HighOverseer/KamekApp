package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.Navigation
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.util.NavigationBarItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@Composable
fun rememberMainPageState(
    navHostController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): MainPageState {
    return remember(
        navHostController,
        coroutineScope
    ) {
        MainPageState(
            navHostController = navHostController,
            coroutineScope = coroutineScope
        )
    }
}

@Stable
class MainPageState(
    private val navHostController: NavHostController,
    private val coroutineScope: CoroutineScope,
) {

    val bottomNavigationBarItems: ImmutableList<NavigationBarItem> = getNavigationBarItems()

    private val topLevelRoutesStringValMap = mapOf(
        Navigation.Main.Home.stringVal to Navigation.Main.Home,
        Navigation.Main.Diagnosis.stringVal to Navigation.Main.Diagnosis,
        Navigation.Main.Account.stringVal to Navigation.Main.Account,
    )

    private val currentSelectedAndNavigatedTopLevelRoute: StateFlow<Navigation.Main.MainRoute?> =
        navHostController.currentBackStackEntryFlow
                .map {
                    val currentRoute = it.destination.route
                    topLevelRoutesStringValMap[currentRoute]
                }
                .distinctUntilChanged()
            .stateIn(
                coroutineScope,
                SharingStarted.WhileSubscribed(5000L),
                null
            )

    private var currentJustSelectedTopLevelRoute by mutableStateOf<Navigation.Main.MainRoute?>(null)

    val selectedBottomNavigationRoute:Navigation.Main.MainRoute?
        get() = currentJustSelectedTopLevelRoute
            ?: currentSelectedAndNavigatedTopLevelRoute.value
            ?: topLevelRoutesStringValMap[navHostController.currentBackStackEntry?.destination?.route]

    val shouldShowTopAppBar: StateFlow<Boolean> =
        currentSelectedAndNavigatedTopLevelRoute
            .map {
                it == Navigation.Main.Diagnosis
            }
            .stateIn(
                coroutineScope,
                SharingStarted.WhileSubscribed(5000L),
                false
            )

    val isInTopLevel = currentSelectedAndNavigatedTopLevelRoute
        .map {
            it != null
        }
        .distinctUntilChanged()
        .stateIn(
            coroutineScope,
            SharingStarted.WhileSubscribed(5000L),
            false
        )

    var isTopBarShown by mutableStateOf(false)
        private set


    @Composable
    fun HandleTopAppBarInitialExpandedHeightEffect(
        shouldShowTopAppBar:Boolean,
        expandTopAppBar: () -> Unit = {}
    ) {
        LaunchedEffect(shouldShowTopAppBar) {
            if (shouldShowTopAppBar) {
                //Wait for Navigation to Diagnosis Finished first
                delay(TOP_APP_BAR_VISIBILITY_ANIMATION_DURATION_MILLIS.times(2).toLong())

                expandTopAppBar()
                isTopBarShown = true
            }
        }
    }

    private var navigationJob: Job? = null
    fun navigateToTopLevel(
        nextDestination: Navigation.Main.MainRoute
    ) {
        val currentSelectedTopLevelRoute = currentSelectedAndNavigatedTopLevelRoute.value

        if (currentSelectedTopLevelRoute == nextDestination) return

        if (navigationJob != null) return

        navigationJob = coroutineScope.launch {
            this@MainPageState.currentJustSelectedTopLevelRoute = nextDestination

            val isExitingDiagnosisRoute = currentSelectedTopLevelRoute == Navigation.Main.Diagnosis
            if (isExitingDiagnosisRoute) {
                isTopBarShown = false
                delay(TOP_APP_BAR_VISIBILITY_ANIMATION_DURATION_MILLIS.toLong())
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
        const val TOP_APP_BAR_VISIBILITY_ANIMATION_DURATION_MILLIS = 200
    }
}