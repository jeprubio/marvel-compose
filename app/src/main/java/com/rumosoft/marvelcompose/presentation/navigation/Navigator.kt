package com.rumosoft.marvelcompose.presentation.navigation

import androidx.navigation3.runtime.NavKey

/**
 * Handles navigation events (forward and back) by updating the navigation state.
 */
class Navigator(val state: NavigationState) {
    fun navigate(route: NavKey) {
        if (route in state.backStacks.keys) {
            // This is a top level route, just switch to it.
            state.topLevelRoute = route
        } else {
            state.backStacks[state.topLevelRoute]?.add(route)
        }
    }

    /**
     * Navigate to a route on a specific tab's back stack.
     * Switches to that tab and pushes the route there.
     */
    fun navigateOnTab(tab: NavKey, route: NavKey) {
        state.topLevelRoute = tab
        state.backStacks[tab]?.add(route)
    }

    /**
     * Atomically pops the current entry, switches to the target tab,
     * and pushes a route there. Avoids the visual glitch of two separate
     * state changes (goBack + navigateOnTab) in the same frame.
     */
    fun switchTabAndNavigate(tab: NavKey, route: NavKey) {
        val currentStack = state.backStacks[state.topLevelRoute]
        currentStack?.removeLastOrNull()
        state.topLevelRoute = tab
        state.backStacks[tab]?.add(route)
    }

    fun goBack() {
        val currentStack = state.backStacks[state.topLevelRoute]
            ?: error("Stack for ${state.topLevelRoute} not found")
        val currentRoute = currentStack.last()

        // If we're at the base of the current route, go back to the start route stack.
        if (currentRoute == state.topLevelRoute) {
            state.topLevelRoute = state.startRoute
        } else {
            currentStack.removeLastOrNull()
        }
    }
}
