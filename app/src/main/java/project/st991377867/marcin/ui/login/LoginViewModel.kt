package project.st991377867.marcin.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import project.st991377867.marcin.data.FirebaseUserLiveData
import project.st991377867.marcin.data.model.User

class LoginViewModel : ViewModel() {

    companion object {
        const val TAG = "LoginViewModel"
        const val SIGN_IN_RESULT_CODE = 1001
    }

    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED, INVALID_AUTHENTICATION
    }

    val authenticationState = FirebaseUserLiveData().map { user ->
        if (user != null) {
            AuthenticationState.AUTHENTICATED
        } else {
            AuthenticationState.UNAUTHENTICATED
        }
    }

    fun addUserData(user: User) {
        User.updateUserData(user)
    }

}
