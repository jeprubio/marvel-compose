package com.rumosoft.marvelcompose.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@Composable
fun BottomNavigationBar(
    navigationItems: List<Tabs>,
    currentRoute: String?,
    onTabClick: (Tabs) -> Unit = {},
) {
    NavigationBar {
        navigationItems.forEach { tab ->
            val tabTitle = stringResource(id = tab.title)
            val selected = currentRoute == tab.route
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = tab.icon),
                        contentDescription = null,
                    )
                },
                label = { Text(tabTitle) },
                selected = selected,
                onClick = { onTabClick(tab) },
                alwaysShowLabel = true,
            )
        }
    }
}

@Preview
@Composable
fun PreviewBottomNavigationBar() {
    MarvelComposeTheme {
        BottomNavigationBar(
            currentRoute = Tabs.Characters.route,
            navigationItems = listOf(
                Tabs.Characters,
                Tabs.Comics,
            ),
        )
    }
}
