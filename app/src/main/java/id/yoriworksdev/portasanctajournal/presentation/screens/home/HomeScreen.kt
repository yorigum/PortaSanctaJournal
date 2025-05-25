package id.yoriworksdev.portasanctajournal.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import id.yoriworksdev.portasanctajournal.R
import id.yoriworksdev.portasanctajournal.data.model.ErrorMessage
import id.yoriworksdev.portasanctajournal.data.model.component.CarouselItem
import id.yoriworksdev.portasanctajournal.data.model.component.uriCarousel
import id.yoriworksdev.portasanctajournal.presentation.screens.signup.SignUpViewModel
import id.yoriworksdev.portasanctajournal.ui.component.carousel.CarouselPorta
import id.yoriworksdev.portasanctajournal.ui.component.image.BackgroundWithGradient
import id.yoriworksdev.portasanctajournal.ui.component.navigation.BottomNavItem

@Composable
fun HomeScreen(list: List<CarouselItem>, openAuthScreen: () -> Unit, showErrorSnackbar: (ErrorMessage) -> Unit, viewModel: HomeViewModel = hiltViewModel()) {

    viewModel.loadCurrentUser()
    LaunchedEffect(viewModel.isAnonymous) {
        if(viewModel.isAnonymous.value){
            viewModel.signOut()
        }
    }
    LaunchedEffect(viewModel.shouldRestartApp) {
        if(viewModel.shouldRestartApp.value){
            openAuthScreen.invoke()
        }else{

        }
    }
    BackgroundWithGradient(true) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            Column {
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

                CarouselPorta(list)
            }

            Button(onClick = { /* Start Ziarah */ }, modifier = Modifier.fillMaxWidth()) {
                Text("Mulai Ziarah Hari Ini")
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.2f))
        }
    }
}
