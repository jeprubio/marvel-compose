package com.rumosoft.marvelcompose.presentation.navigation

import androidx.navigation.NavHostController

fun onTabClick(
    tab: Tabs,
    navController: NavHostController,
) {
    if (tab.route != navController.currentDestination?.route) {
        navController.navigate(tab.route) {
            navController.currentDestination?.route?.let {
                popUpTo(it) {
                    saveState = true
                    inclusive = true
                }
            }

            launchSingleTop = true
        }
    }
}
