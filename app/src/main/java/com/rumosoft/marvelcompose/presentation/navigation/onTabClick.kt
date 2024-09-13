package com.rumosoft.marvelcompose.presentation.navigation

import androidx.navigation.NavHostController

fun onTabClick(
    tab: Tabs,
    navController: NavHostController,
) {
    navController.navigate(tab.screen) {
        popUpTo(tab.screen) {
            saveState = true
            inclusive = true
        }
        launchSingleTop = true
    }
}
