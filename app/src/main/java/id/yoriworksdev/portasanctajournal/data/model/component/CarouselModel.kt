package id.yoriworksdev.portasanctajournal.data.model.component



val uriCarousel = "https://picsum.photos/id/${(1..126).random()}/1200/1600"

data class CarouselItem(
    val id: Int, val imageUri: String, val contentDescription: String
)