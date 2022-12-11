package project.st991377867.marcin.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import project.st991377867.marcin.data.model.User

class ProfileViewModel : ViewModel() {

    private val TAG = "ProfileViewModel"
    private var user: LiveData<User> = User.requestLiveUserData()

    /**
     * Get live user data
     *
     * @return live user data
     */
    fun getLiveUserData() : LiveData<User> {
        return user
    }

    /**
     * Get synchronization status of user data
     *
     * @return live synchronization status
     */
    fun getSyncStatus() : LiveData<Boolean> {
        return User.isSynced()
    }

    /**
     * Update user data in the backend
     */
    fun updateUserData() {
        User.updateUserData(user.value!!)
    }

    /**
     * Called when the view model is no longer used and is being destroyed
     */
    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "ProfileViewModel destroyed!")
    }

}
