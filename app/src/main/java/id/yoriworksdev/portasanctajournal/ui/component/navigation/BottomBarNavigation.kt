package id.yoriworksdev.portasanctajournal.ui.component.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import id.yoriworksdev.portasanctajournal.R

sealed class BottomNavItem(val route: String, val icon: Int,val iconSelected:Int, val label: String) {
    data object Home : BottomNavItem("home", R.drawable.house_simple_thin, R.drawable.house_fill,"Beranda")
    data object Map : BottomNavItem("map", R.drawable.compass_rose_thin, R.drawable.compass_rose_fill,"Peta")
    data object Paspor : BottomNavItem("paspor",  R.drawable.address_book_thin,R.drawable.address_book_fill, "Paspor")
    data object Doa : BottomNavItem("doa", R.drawable.hands_praying_thin,R.drawable.hands_praying_fill,"Doa")
    data object Profile : BottomNavItem("profile", R.drawable.user_circle_thin, R.drawable.user_circle_fill,"Akun")
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Map,
        BottomNavItem.Paspor,
        BottomNavItem.Doa,
        BottomNavItem.Profile
    )

    BottomNavigation(
        backgroundColor = Color.Transparent,
        elevation = 2.dp,
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        items.forEach { topLevelRoute ->
            val isSelected = navBackStackEntry?.destination?.route == topLevelRoute.route
            val icon = if(isSelected) topLevelRoute.iconSelected else topLevelRoute.icon
            Log.i("INFO", "BottomNavigationBar: $isSelected\n${navBackStackEntry?.destination?.route}")
            BottomNavigationItem(
                icon = { Icon(painterResource(icon), contentDescription = topLevelRoute.route, modifier = Modifier.size(48.dp)) },
                label = { Text(topLevelRoute.label) },
                alwaysShowLabel = false,
                selectedContentColor = Color.Green,
                unselectedContentColor = Color.LightGray,
                selected = isSelected,
                onClick = {
                    navController.navigate(topLevelRoute.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}
