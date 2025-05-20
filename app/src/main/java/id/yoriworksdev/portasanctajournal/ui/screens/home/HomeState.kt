package id.yoriworksdev.portasanctajournal.ui.screens.home

import id.yoriworksdev.portasanctajournal.data.model.home.Gereja

data class HomeState(
    val daftarGereja: List<Gereja> = emptyList(),
    val jumlahDikunjungi: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)