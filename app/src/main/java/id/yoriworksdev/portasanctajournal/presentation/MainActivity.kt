package id.yoriworksdev.portasanctajournal.presentation

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.yoriworksdev.portasanctajournal.ui.component.navigation.BottomNavItem
import id.yoriworksdev.portasanctajournal.presentation.screens.home.HomeScreen
import id.yoriworksdev.portasanctajournal.ui.component.navigation.BottomNavigationBar
import id.yoriworksdev.portasanctajournal.ui.theme.PortaSanctaJournalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            PortaSanctaJournalTheme {

                Scaffold(modifier = Modifier,
                    bottomBar = {
                        BottomNavigationBar(navController)

                    }) {pv->
                    NavHost(navController, startDestination = "home", modifier = Modifier.padding(pv)){
                        composable(BottomNavItem.Home.route){
                            HomeScreen()
                        }
                        composable(BottomNavItem.Map.route){
                            Text("Map")
                        }
                        composable(BottomNavItem.Paspor.route){
                            Text("Journal")
                        }
                        composable(BottomNavItem.Profile.route){
                            Text("Profile")
                        }
                        composable(BottomNavItem.Doa.route){
                            Text("Doa")
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true, device = "id:pixel_5", showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.NONE
)
@Composable
fun GreetingPreview() {
    PortaSanctaJournalTheme {
        Greeting("Android")
    }
}