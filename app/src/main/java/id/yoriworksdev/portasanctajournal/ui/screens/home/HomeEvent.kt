package id.yoriworksdev.portasanctajournal.ui.screens.home

sealed class HomeEvent {
    object LoadData : HomeEvent()
    data class CheckIn(val gerejaId: String) : HomeEvent()
}