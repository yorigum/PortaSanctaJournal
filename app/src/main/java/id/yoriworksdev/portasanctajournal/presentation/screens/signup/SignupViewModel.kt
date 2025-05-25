package id.yoriworksdev.portasanctajournal.presentation.screens.signup

import dagger.hilt.android.lifecycle.HiltViewModel
import id.yoriworksdev.portasanctajournal.MainViewModel
import id.yoriworksdev.portasanctajournal.R
import id.yoriworksdev.portasanctajournal.data.model.ErrorMessage
import id.yoriworksdev.portasanctajournal.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : MainViewModel() {
    private val _shouldRestartApp = MutableStateFlow(false)
    val shouldRestartApp: StateFlow<Boolean>
        get() = _shouldRestartApp.asStateFlow()

    fun signUp(
        email: String,
        password: String,
        repeatPassword: String,
        showErrorSnackbar: (ErrorMessage) -> Unit
    ) {
        if (!email.isValidEmail()) {
            showErrorSnackbar(ErrorMessage.IdError(R.string.invalid_email))
            return
        }

        if (!password.isValidPassword()) {
            showErrorSnackbar(ErrorMessage.IdError(R.string.invalid_password))
            return
        }

        if (password != repeatPassword) {
            showErrorSnackbar(ErrorMessage.IdError(R.string.passwords_do_not_match))
            return
        }

        launchCatching(showErrorSnackbar) {
            authRepository.signUp(email, password)
            _shouldRestartApp.value = true
        }
    }
}