package com.rumosoft.marvelcompose.presentation.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    navigationItems: List<Tabs>,
    onAppBack: () -> Unit = {},
) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        navigationItems.forEach { tab ->
            val tabTitle = stringResource(id = tab.title)
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = tab.icon),
                        contentDescription = tabTitle
                    )
                },
                label = { Text(tabTitle) },
                selected = currentRoute == tab.route,
                onClick = {
                    onTabClick(tab, navController)
                },
                alwaysShowLabel = true
            )
        }
    }

    BackHandler {
        onAppBack()
    }
}

private fun onTabClick(
    tab: Tabs,
    navController: NavHostController
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
