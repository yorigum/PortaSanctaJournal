package id.yoriworksdev.portasanctajournal.presentation.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoginScreen(){
    Scaffold {pv->
        Column(Modifier.fillMaxSize().padding(pv),horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center)  {
            Text("Login")
        }
    }
}

@Composable
fun SignUpScreen(){
    Scaffold {pv->
        Column(Modifier.fillMaxSize().padding(pv),horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center)  {
            Text("Sign Up")
        }
    }
}
