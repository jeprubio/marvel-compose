package com.rumosoft.marvelcompose.presentation.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavigationRailBar(
    navController: NavHostController,
    navigationItems: List<Tabs>,
    onAppBack: () -> Unit = {},
) {
    NavigationRail {
        Spacer(modifier = Modifier.height(8.dp))
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        navigationItems.forEach { tab ->
            val tabTitle = stringResource(id = tab.title)
            val selected = currentRoute == tab.route
            NavigationRailItem(
                icon = {
                    Icon(
                        painter = painterResource(id = tab.icon),
                        contentDescription = null,
                    )
                },
                label = { Text(tabTitle) },
                selected = selected,
                onClick = {
                    onTabClick(tab, navController)
                },
                alwaysShowLabel = true,
            )
        }
    }

    BackHandler {
        onAppBack()
    }
}
