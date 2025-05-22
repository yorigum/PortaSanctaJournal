package id.yoriworksdev.portasanctajournal.ui.component.carousel

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import id.yoriworksdev.portasanctajournal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarouselPorta(){
    data class CarouselItem(
        val id: Int, val imageUri: String, val contentDescription: String
    )

    val uri = "https://picsum.photos/id/${(1..126).random()}/1200/1600"


    val items = remember {
        listOf(
            CarouselItem(
                0,
                "https://picsum.photos/id/${(1..126).random()}/1200/1600",
                "cupcake"
            ),
            CarouselItem(
                1,
                "https://picsum.photos/id/${(1..126).random()}/1200/1600",
                "donut"
            ),
            CarouselItem(
                2, "https://picsum.photos/id/${(1..126).random()}/1200/1600",
                "eclair"
            ),
            CarouselItem(
                3,
                "https://picsum.photos/id/${(1..126).random()}/1200/1600",
                "froyo"
            ),

            )
    }

    HorizontalMultiBrowseCarousel(
        state = rememberCarouselState { items.count() },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 16.dp, bottom = 16.dp),
        preferredItemWidth = 186.dp,
        itemSpacing = 8.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) { i ->
        val item = items[i]
        val model = ImageRequest.Builder(LocalContext.current)
            .data(item.imageUri)
            .build()
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(8.dp).clip(RoundedCornerShape(16.dp)).shadow(2.dp)){
            val painter = rememberAsyncImagePainter(model)
            val state by painter.state.collectAsState()

            when (state) {
                is AsyncImagePainter.State.Empty,
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator()
                    Log.d("CarouselPorta", "Loading")
                }
                is AsyncImagePainter.State.Success -> {
                    Image(
                        painter = painter,
                        contentDescription = item.contentDescription
                    )
                    Log.d("CarouselPorta", "Success")
                }
                is AsyncImagePainter.State.Error -> {
                    Log.d("CarouselPorta", "Error")
                }
            }
        }

    }
}