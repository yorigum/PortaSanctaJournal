package id.yoriworksdev.portasanctajournal.presentation.screens.home

sealed class HomeEvent {
    object LoadData : HomeEvent()
    data class CheckIn(val gerejaId: String) : HomeEvent()
}