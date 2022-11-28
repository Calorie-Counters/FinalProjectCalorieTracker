package project.st991377867.marcin.ui.notifications

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import project.st991377867.marcin.data.model.Notification

class NotificationsViewModel : ViewModel() {

    val fireStoreDatabase = FirebaseFirestore.getInstance()
    val firebaseUserID = FirebaseAuth.getInstance().currentUser?.uid
    val notificationList: MutableList<Notification> = mutableListOf()
    
    fun getNotifications() : MutableList<Notification> {
        val notifications: MutableList<Notification> = mutableListOf()

        if (firebaseUserID != null){
            fireStoreDatabase.collection("notifications")
                .whereEqualTo("uid", firebaseUserID)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (document in it.result){
                            try {
                                val id = document.data.getValue("id") as String
                                val title = document.data.getValue("title") as String
                                val date = document.data.getValue("date") as String
                                val time = document.data.getValue("time") as String
                                val description = document.data.getValue("description") as String
                                val reminder = Notification(id, title, date, time, description)
                                notifications.add(reminder)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
        }
        return notifications
    }
    
    fun getDummyNotifications() : MutableList<Notification> {
        val notifications: MutableList<Notification> = mutableListOf()
        val notification1 =
            Notification("1", "Notification 1", "01/01/2021", "12:00", "Description 1")
        val notification2 =
            Notification("2", "Notification 2", "01/01/2021", "12:00", "Description 2")
        val notification3 =
            Notification("3", "Notification 3", "01/01/2021", "12:00", "Description 3")
        val notification4 =
            Notification("4", "Notification 4", "01/01/2021", "12:00", "Description 4")
        val notification5 =
            Notification("5", "Notification 5", "01/01/2021", "12:00", "Description 5")
        notifications.add(notification1)
        notifications.add(notification2)
        notifications.add(notification3)
        notifications.add(notification4)
        notifications.add(notification5)
        return notifications
    }
    
}