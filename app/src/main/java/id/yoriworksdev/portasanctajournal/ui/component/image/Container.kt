package id.yoriworksdev.portasanctajournal.ui.component.image

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import id.yoriworksdev.portasanctajournal.R

@Composable
fun BackgroundWithGradient(withGradient: Boolean,content: @Composable () -> Unit){
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(
                id = R.drawable.pexels_samuelgoldberg_17581309
            ),
            contentDescription = "background",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(
                color = Color.Cyan.copy(alpha = 0.8f),
                blendMode = BlendMode.Screen
            ),
            modifier = Modifier.fillMaxSize()
        )
        AnimatedVisibility(withGradient) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Transparent, Color.Black.copy(alpha = 1f)
                            ), start = Offset.Zero, end = Offset(0f, Float.POSITIVE_INFINITY)
                        )
                    )
            )
        }
        content.invoke()


    }
}