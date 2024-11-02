package com.neotelemetrixgdscunand.kamekapp.presentation.ui.toplevel.util

import com.neotelemetrixgdscunand.kamekapp.presentation.ui.BottomBarRoute

fun isInTopLevelPage(currentRoute:String?):Boolean {
    return currentRoute == BottomBarRoute.Home::class.java.canonicalName
            || currentRoute == BottomBarRoute.Diagnosis::class.java.canonicalName
            || currentRoute == BottomBarRoute.Account::class.java.canonicalName

}