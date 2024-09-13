package com.rumosoft.marvelcompose.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rumosoft.components.presentation.deeplinks.CharactersScreen
import com.rumosoft.components.presentation.deeplinks.Screen
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@Composable
fun BottomNavigationBar(
    navigationItems: List<Tabs>,
    currentScreen: Screen,
    onTabClick: (Tabs) -> Unit = {},
) {
    NavigationBar {
        navigationItems.forEach { tab ->
            val tabTitle = stringResource(id = tab.title)
            val selected = currentScreen == tab.screen
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
                colors = NavigationBarItemDefaults.colors().copy(
                    selectedIconColor = MarvelComposeTheme.colors.primary,
                    selectedIndicatorColor = MarvelComposeTheme.colors.onPrimary,
                    selectedTextColor = MarvelComposeTheme.colors.onPrimary,
                    unselectedIconColor = MarvelComposeTheme.colors.onPrimaryContainer,
                    unselectedTextColor = MarvelComposeTheme.colors.onPrimaryContainer,
                ),
            )
        }
    }
}

@Preview
@Composable
fun PreviewBottomNavigationBar() {
    MarvelComposeTheme {
        BottomNavigationBar(
            currentScreen = CharactersScreen,
            navigationItems = listOf(
                Tabs.Characters,
                Tabs.Comics,
            ),
        )
    }
}
