package project.st991377867.marcin.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import project.st991377867.marcin.data.model.User

class ProfileViewModel : ViewModel() {

    private val TAG = "ProfileViewModel"
    private var user: LiveData<User> = User.requestLiveUserData()

    fun getLiveUserData() : LiveData<User> {
        return user
    }

    fun getSyncStatus() : LiveData<Boolean> {
        return User.isSynced()
    }

    fun updateUserData() {
        User.updateUserData(user.value!!)
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "ProfileViewModel destroyed!")
    }



}