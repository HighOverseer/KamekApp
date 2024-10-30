package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey90
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.BottomBarRoute
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.BottomNavigationBar
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component.getBottomBarItems
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.screen.AccountScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.screen.DiagnosisScreen
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.screen.HomeScreen

@Composable
fun TopLevelPage(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
) {

    val navigationBarItems = remember {
        getBottomBarItems()
    }

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        containerColor = Grey90,
        modifier = modifier,
        bottomBar = {
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
                DiagnosisScreen()
            }

            composable<BottomBarRoute.Account> {
                AccountScreen()
            }

        }
    }
}


@Preview
@Composable
private fun TopLevelPagePreview() {
    KamekAppTheme {
        TopLevelPage()
    }
}
