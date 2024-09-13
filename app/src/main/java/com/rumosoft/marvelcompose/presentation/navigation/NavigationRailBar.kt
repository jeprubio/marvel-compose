package com.rumosoft.marvelcompose.presentation.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rumosoft.components.presentation.deeplinks.CharactersScreen
import com.rumosoft.components.presentation.deeplinks.Screen
import com.rumosoft.components.presentation.theme.MarvelComposeTheme

@Composable
fun NavigationRailBar(
    navigationItems: List<Tabs>,
    currentScreen: Screen,
    onTabClick: (Tabs) -> Unit = {},
) {
    NavigationRail {
        Spacer(modifier = Modifier.height(8.dp))
        navigationItems.forEach { tab ->
            val tabTitle = stringResource(id = tab.title)
            val selected = currentScreen == tab.screen
            NavigationRailItem(
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
fun PreviewNavigationRailBar() {
    MarvelComposeTheme {
        NavigationRailBar(
            currentScreen = CharactersScreen,
            navigationItems = listOf(
                Tabs.Characters,
                Tabs.Comics,
            )
        )
    }
}
