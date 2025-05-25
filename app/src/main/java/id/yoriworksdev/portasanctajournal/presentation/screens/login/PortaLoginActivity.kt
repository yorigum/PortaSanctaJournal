package id.yoriworksdev.portasanctajournal.presentation.screens.login

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import id.yoriworksdev.portasanctajournal.data.model.ErrorMessage
import id.yoriworksdev.portasanctajournal.presentation.screens.home.HomeScreen
import id.yoriworksdev.portasanctajournal.presentation.screens.signup.SignUpScreen
import id.yoriworksdev.portasanctajournal.ui.component.navigation.BottomNavItem
import id.yoriworksdev.portasanctajournal.ui.component.navigation.BottomNavigationBar
import id.yoriworksdev.portasanctajournal.ui.theme.PortaSanctaJournalTheme
import id.yoriworksdev.portasanctajournal.utils.Auth
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PortaLoginActivity : ComponentActivity() {
    private var isSignedUp: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSoftInputMode()
        enableEdgeToEdge()
        setContent {
            val scope = rememberCoroutineScope()
            val snackbarHostState = remember { SnackbarHostState() }
            var isSignedUp by remember {
                mutableStateOf( intent.getBooleanExtra(Auth.IS_SIGNUP, false))
            }
            PortaSanctaJournalTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Color.White
                ) {
                    Scaffold( snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) {_->
                        when (isSignedUp) {
                            true -> {
                                SignUpScreen(
                                    openHomeScreen = {
                                       onBackPressedDispatcher.onBackPressed()
                                    },
                                    showErrorSnackbar = { errorMessage ->
                                        val message = getErrorMessage(errorMessage)
                                        scope.launch { snackbarHostState.showSnackbar(message) }
                                    }
                                )
                            }

                            else -> {
                                LoginScreen(openHomeScreen = {
                                    onBackPressedDispatcher.onBackPressed()
                                }, openSignUpScreen = {
                                    isSignedUp = true
                                }, showErrorSnackbar = { errorMessage ->
                                    val message = getErrorMessage(errorMessage)
                                    scope.launch { snackbarHostState.showSnackbar(message) }
                                })
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
            is ErrorMessage.IdError -> this@PortaLoginActivity.getString(error.message)
        }
    }
}