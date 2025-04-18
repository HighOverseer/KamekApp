package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel

import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Grey65
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.KamekAppTheme
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Maroon55
import com.neotelemetrixgdscunand.kamekapp.presentation.theme.Shadow
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.Navigation
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.util.NavigationBarItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navigationBarItems: ImmutableList<NavigationBarItem> = persistentListOf(),
    onSelectedNavigation: (Navigation.Main.MainRoute) -> Unit = {},
    selectedNavigationProvider: @Composable () -> Navigation.Main.MainRoute? = { null }
) {

    BottomAppBar(
        modifier = modifier
            .shadow(
                elevation = 16.dp,
                spotColor = Shadow
            ),
        containerColor = Color.White
    ) {
        val selectedNavigation = selectedNavigationProvider()
        navigationBarItems.forEach {
            val isSelected = selectedNavigation == it.route

            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = Grey65,
                    selectedIconColor = Maroon55,
                    unselectedTextColor = Grey65,
                    selectedTextColor = Maroon55,
                    indicatorColor = Color.White,

                    ),

                selected = isSelected,
                icon = {
                    Icon(
                        modifier = Modifier.size(27.dp),
                        imageVector = ImageVector.vectorResource(
                            id = it.iconResId
                        ),
                        contentDescription = stringResource(it.titleRestId)
                    )
                },
                onClick = {
                    onSelectedNavigation(it.route)
                },
                label = {
                    Text(
                        text = stringResource(it.titleRestId),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            )
        }
    }
}


@Preview
@Composable
private fun BottomNavigationBarPreview() {
    KamekAppTheme {
        BottomNavigationBar(
            navigationBarItems = rememberMainPageState()
                .bottomNavigationBarItems
        )
    }
}