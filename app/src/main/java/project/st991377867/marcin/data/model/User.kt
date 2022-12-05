package project.st991377867.marcin.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * User
 *
 * @constructor Create empty User
 */
class User {

    val uid: String
        get() = field

    var displayName: String
        get() = field

    var firstName: String
        get() = field
        set(value) { field = value }

    var lastName: String
        get() = field
        set(value) { field = value }

    var address: String
        get() = field
        set(value) { field = value }

    var city: String
        get() = field
        set(value) { field = value }

    var country: String
        get() = field
        set(value) { field = value }

    var postalCode: String
        get() = field
        set(value) { field = value }

    var goals: List<Goal>
        get() = field
        set(value) { field = value }

    var email: String
        get() = FirebaseAuth.getInstance().currentUser!!.email!!

    constructor(uid: String) {
        this.uid = uid
        displayName = FirebaseAuth.getInstance().currentUser!!.displayName!!
        firstName = ""
        lastName = ""
        email = FirebaseAuth.getInstance().currentUser!!.email!!
        address = ""
        city = ""
        country = ""
        postalCode = ""
        goals = listOf()
    }

    companion object {
        private val TAG = "User"

        private var user: User = User(FirebaseAuth.getInstance().currentUser!!.uid)
        private var userLiveData: MutableLiveData<User> = MutableLiveData(user)
        private var syncFlagLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

        fun isSynced() : LiveData<Boolean> { return syncFlagLiveData }

        fun getUser(request: Boolean = true) : User {
            if (request) { requestUserData() }
            return user
        }

        /**
         * Request Live User Data while requestUserData() is called
         *
         * @return LiveData<User>
         */
        fun requestLiveUserData() : LiveData<User> {
            requestUserData()
            return userLiveData
        }

        /**
         * Update user data
         *
         * @param user User to update
         */
        fun updateUserData(user: User) {
            val userHashMap = getUserHashMap(user)
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("users").document(user.uid)
            docRef.set(userHashMap)
                .addOnSuccessListener { Log.i(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.i(TAG, "Error writing document", e) }
            userLiveData.value = user
        }

        /**
         * Request user data from Firestore
         *
         */
        private fun requestUserData() {
            Log.i(TAG, "User: ${user}")
            syncFlagLiveData.value = false
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("users").document(user.uid)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.i(TAG, "DocumentSnapshot data: ${document.data}")
                        user.firstName = document.data?.get("firstName").toString()
                        user.lastName = document.data?.get("lastName").toString()
                        user.address = document.data?.get("address").toString()
                        user.city = document.data?.get("city").toString()
                        user.country = document.data?.get("country").toString()
                        user.postalCode = document.data?.get("postalCode").toString()
                        userLiveData.value = user
                        Log.i(TAG, "UserliveData value: ${userLiveData.value!!.firstName}")
                    } else {
                        updateUserData(user)
                        Log.i(TAG, "No such document")
                    }
                    syncFlagLiveData.value = true
                }
                .addOnFailureListener { exception ->
                    Log.i(TAG, "get failed with ", exception)
                    syncFlagLiveData.value = true
                }
        }

        /**
         * Get user hash map from user object
         *
         * @param user User to get hash map from
         * @return HashMap<String, Any>
         */
        private fun getUserHashMap(user: User) : HashMap<String, Any> {
            val userMap = HashMap<String, Any>()
            userMap["uid"] = user.uid
            userMap["firstName"] = user.firstName
            userMap["lastName"] = user.lastName
            userMap["address"] = user.address
            userMap["city"] = user.city
            userMap["country"] = user.country
            userMap["postalCode"] = user.postalCode
            return userMap
        }

    }

}