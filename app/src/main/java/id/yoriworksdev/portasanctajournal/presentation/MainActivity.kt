package id.yoriworksdev.portasanctajournal.presentation

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.yoriworksdev.portasanctajournal.ui.component.navigation.BottomNavItem
import id.yoriworksdev.portasanctajournal.presentation.screens.home.HomeScreen
import id.yoriworksdev.portasanctajournal.presentation.screens.login.PortaLoginActivity
import id.yoriworksdev.portasanctajournal.ui.component.navigation.BottomNavigationBar
import id.yoriworksdev.portasanctajournal.ui.theme.PortaSanctaJournalTheme

class MainActivity : ComponentActivity() {
    val db = Firebase.firestore
    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analytics = Firebase.analytics
        enableEdgeToEdge()
        // Create a new user with a first and last name
        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )

// Add a new document with a generated ID
        setContent {
            val navController = rememberNavController()

                PortaSanctaJournalTheme {
                    Surface(color = Color.Transparent) {
                    Scaffold(modifier = Modifier.background(Color.Transparent),
                        contentWindowInsets = WindowInsets(0,0,0,0),
                        bottomBar = {}){pv->
                        Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize().padding(pv)) {
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
                                    val notes = remember { mutableStateListOf<HashMap<String,String>>() }
                                    val notesCollection = db.collection("users")

                                    LaunchedEffect(Unit) {
                                        notesCollection.get()
                                            .addOnSuccessListener { result ->
                                                for (document in result) {
                                                    val note = document.data as HashMap<*, *>
                                                    notes.add(note as HashMap<String, String>)
                                                }
                                            }
                                            .addOnFailureListener { exception ->
                                                // Handle error
                                            }
                                    }
                                    LazyColumn(Modifier.fillMaxSize(1f).padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                        stickyHeader {
                                            Text("Profile")
                                        }
                                        item{
                                            Button(onClick = {
                                                val intent = Intent(this@MainActivity, PortaLoginActivity::class.java)
                                                startActivity(intent)
                                            }) {
                                                Text("Login")
                                                analytics.logEvent("login_button_clicked", null)
                                            }
                                        }
                                    }


                                }
                                composable(BottomNavItem.Doa.route){
                                    Text("Doa")
                                }
                            }
                            Surface(color = Color.Transparent) {
                                BottomNavigationBar(navController)
                            }
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