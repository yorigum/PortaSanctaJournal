package id.yoriworksdev.portasanctajournal.presentation

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import dagger.hilt.android.AndroidEntryPoint
import id.yoriworksdev.portasanctajournal.data.model.ErrorMessage
import id.yoriworksdev.portasanctajournal.data.model.component.CarouselItem
import id.yoriworksdev.portasanctajournal.data.model.component.uriCarousel
import id.yoriworksdev.portasanctajournal.ui.component.navigation.BottomNavItem
import id.yoriworksdev.portasanctajournal.presentation.screens.home.HomeScreen
import id.yoriworksdev.portasanctajournal.presentation.screens.login.PortaLoginActivity
import id.yoriworksdev.portasanctajournal.ui.component.navigation.BottomNavigationBar
import id.yoriworksdev.portasanctajournal.ui.theme.PortaSanctaJournalTheme
import id.yoriworksdev.portasanctajournal.utils.Auth
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val db = Firebase.firestore
    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analytics = Firebase.analytics
        enableEdgeToEdge()
        val list = mutableListOf<CarouselItem>()
        for (i in 1..126) {
            val uri = "https://picsum.photos/id/${(1..126).random()}/1200/1600"
            list.add(CarouselItem(i, uri, "Title $i. $uriCarousel"))
        }

// Add a new document with a generated ID
        setContent {
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()
            setSoftInputMode()

            PortaSanctaJournalTheme {
                Surface(color = Color.Transparent) {
                    Scaffold(
                        modifier = Modifier.background(Color.Transparent),
                        contentWindowInsets = WindowInsets(0, 0, 0, 0),
                        bottomBar = {},
                        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { pv ->
                        Box(
                            contentAlignment = Alignment.BottomCenter,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(pv)
                        ) {
                            NavHost(
                                navController,
                                startDestination = "home",
                                modifier = Modifier.padding(pv)
                            ) {
                                composable(BottomNavItem.Home.route) {
                                    HomeScreen(list, openAuthScreen = {
                                        val intent = Intent(
                                            this@MainActivity,
                                            PortaLoginActivity::class.java
                                        )
                                        intent.putExtra(Auth.IS_SIGNUP, true)
                                        startActivity(intent)
                                    }, showErrorSnackbar = { errorMessage ->
                                        val message = getErrorMessage(errorMessage)
                                        scope.launch { snackbarHostState.showSnackbar(message) }
                                    })
                                }
                                composable(BottomNavItem.Map.route) {
                                    Text("Map")
                                }
                                composable(BottomNavItem.Paspor.route) {
                                    Text("Journal")
                                }
                                composable(BottomNavItem.Profile.route) {
                                    val notes =
                                        remember { mutableStateListOf<HashMap<String, String>>() }
                                    val notesCollection = db.collection("users")

                                    LaunchedEffect(Unit) {
                                        notesCollection.get().addOnSuccessListener { result ->
                                                for (document in result) {
                                                    val note = document.data as HashMap<*, *>
                                                    notes.add(note as HashMap<String, String>)
                                                }
                                            }.addOnFailureListener { exception ->
                                                // Handle error
                                            }
                                    }
                                    LazyColumn(
                                        Modifier
                                            .fillMaxSize(1f)
                                            .padding(8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        stickyHeader {
                                            Text("Profile")
                                        }
                                        item {
                                            Button(onClick = {
                                                val intent = Intent(
                                                    this@MainActivity,
                                                    PortaLoginActivity::class.java
                                                )
                                                startActivity(intent)
                                            }) {
                                                Text("Login")
                                                analytics.logEvent("login_button_clicked", null)
                                            }
                                        }
                                        item {
                                            Button(onClick = {
                                                val intent = Intent(
                                                    this@MainActivity,
                                                    PortaLoginActivity::class.java
                                                )
                                                intent.putExtra(Auth.IS_SIGNUP, true)
                                                startActivity(intent)
                                            }) {
                                                Text("Sign Up")
                                                analytics.logEvent("login_button_clicked", null)
                                            }
                                        }
                                    }


                                }
                                composable(BottomNavItem.Doa.route) {
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

    private fun setSoftInputMode() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

    private fun getErrorMessage(error: ErrorMessage): String {
        return when (error) {
            is ErrorMessage.StringError -> error.message
            is ErrorMessage.IdError -> this@MainActivity.getString(error.message)
        }
    }
}