package id.yoriworksdev.portasanctajournal.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.yoriworksdev.portasanctajournal.ui.component.navigation.BottomNavItem
import id.yoriworksdev.portasanctajournal.ui.component.navigation.BottomNavigationBar

@Composable
fun HomeScreen() {
    Column(
            modifier = Modifier
                .fillMaxSize()
            ) {
        Text("Halo, Yohanes 👋", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(8.dp))
        Text("Kamu telah mengunjungi 4 dari 69 gereja")
        LinearProgressIndicator(
            progress = { 4f / 69f },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(8.dp)),
            color = Color(0xFF2196F3),
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text("Info Terbaru", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Misa Pembukaan Yubileum 2 Juni")
                Text("Katedral Jakarta", style = MaterialTheme.typography.bodySmall)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Start Ziarah */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Mulai Ziarah Hari Ini")
        }
    }

}

@Preview(showSystemUi = true, showBackground = true, device = "id:pixel_xl")
@Composable
fun Preview(){
    val navController = rememberNavController()
    Scaffold(modifier = Modifier.padding(8.dp),
        bottomBar = {
            Row(modifier = Modifier.background(color=Color.White,shape = RoundedCornerShape(100.dp)).shadow(2.dp, shape = RoundedCornerShape(100.dp)).padding(8.dp).fillMaxWidth(1f), horizontalArrangement = Arrangement.SpaceAround){
                for(i in 0..2){
                    IconButton(
                        onClick = {
                            when(i){
                                0 -> navController.navigate(BottomNavItem.Home.route)
                                1 -> navController.navigate(BottomNavItem.Map.route)
                                2 -> navController.navigate(BottomNavItem.Paspor.route)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = BottomNavItem.Home.icon,
                            contentDescription = BottomNavItem.Home.label
                        )
                    }

                }
            }
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