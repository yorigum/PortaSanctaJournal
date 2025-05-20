package id.yoriworksdev.portasanctajournal.data.model.home

data class Gereja(
    val id: String = "",
    val nama: String = "",
    val alamat: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val sudahDikunjungi: Boolean = false
)