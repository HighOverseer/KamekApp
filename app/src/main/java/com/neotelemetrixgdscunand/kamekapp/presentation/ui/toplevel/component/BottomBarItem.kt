package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.component

import com.neotelemetrixgdscunand.kamekapp.R
import com.neotelemetrixgdscunand.kamekapp.presentation.ui.BottomBarRoute

data class BottomBarItem(
    val titleRestId: Int,
    val iconResId: Int,
    val route: BottomBarRoute
)


fun getBottomBarItems(): List<BottomBarItem> {

    return listOf(
        BottomBarItem(
            titleRestId = R.string.beranda,
            iconResId = R.drawable.ic_home,
            route = BottomBarRoute.Home
        ),
        BottomBarItem(
            titleRestId = R.string.diagnosis,
            iconResId = R.drawable.ic_camera,
            route = BottomBarRoute.Diagnosis
        ),
        BottomBarItem(
            titleRestId = R.string.akun,
            iconResId = R.drawable.ic_person,
            route = BottomBarRoute.Account
        ),
    )
}
