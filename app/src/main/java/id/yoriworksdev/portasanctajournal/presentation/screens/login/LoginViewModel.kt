package id.yoriworksdev.portasanctajournal.presentation.screens.login



import dagger.hilt.android.lifecycle.HiltViewModel
import id.yoriworksdev.portasanctajournal.MainViewModel
import id.yoriworksdev.portasanctajournal.data.model.ErrorMessage
import id.yoriworksdev.portasanctajournal.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : MainViewModel() {
    private val _shouldRestartApp = MutableStateFlow(false)
    val shouldRestartApp: StateFlow<Boolean>
        get() = _shouldRestartApp.asStateFlow()

    fun signIn(
        email: String,
        password: String,
        showErrorSnackbar: (ErrorMessage) -> Unit
    ) {
        launchCatching(showErrorSnackbar) {
            authRepository.signIn(email, password)
            _shouldRestartApp.value = true
        }
    }
}