package id.dayona.eleanorpokemondatabase.ui

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BarItem(val title: String, val route: String, val vector: ImageVector)

private val bottomNavItems = listOf(
    BarItem(
        title = "Home",
        route = "home-screen",
        vector = Icons.Rounded.Home,
    ),
    BarItem(
        title = "Search",
        route = "search-screen",
        vector = Icons.Rounded.Search,
    ),
    BarItem(
        title = "Feed",
        route = "feed-screen",
        vector = Icons.Rounded.Star,
    ),
    BarItem(
        title = "Profile",
        route = "profile-screen",
        vector = Icons.Rounded.Person,
    ),
)

interface BottomNav {
    val navController: NavHostController

    @Composable
    fun BottomNavigator() {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        BottomNavigation {
            bottomNavItems.forEach { navItem ->
                BottomNavigationItem(
                    selected = currentRoute == navItem.route,
                    onClick = {
                        navController.navigate(navItem.route) {
                            popUpTo(navController.graph.id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },

                    icon = {
                        Icon(
                            imageVector = navItem.vector,
                            contentDescription = navItem.title
                        )
                    },
                    label = {
                        Text(text = navItem.title)
                    },
                )
            }
        }
    }
}