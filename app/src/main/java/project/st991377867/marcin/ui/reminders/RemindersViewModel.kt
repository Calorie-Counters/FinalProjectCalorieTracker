package project.st991377867.marcin.ui.reminders

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import project.st991377867.marcin.data.model.Reminder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class RemindersViewModel : ViewModel() {

    val fireStoreDatabase = FirebaseFirestore.getInstance()
    val firebaseUserID = FirebaseAuth.getInstance().currentUser?.uid
    val reminderList: MutableList<Reminder> = mutableListOf()

    fun getReminders() : MutableList<Reminder> {
        val reminders: MutableList<Reminder> = mutableListOf()

        if (firebaseUserID != null){
            fireStoreDatabase.collection("reminders")
                .whereEqualTo("uid", firebaseUserID)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (document in it.result){
                            try {
                                val id = document.data.getValue("id") as String
                                val userId = document.data.getValue("uid") as String
                                val name = document.data.getValue("name") as String
                                val date = document.data.getValue("date") as String
                                val time = document.data.getValue("time") as String
                                val description = document.data.getValue("description") as String
                                val reminder = Reminder(id, userId, name, date, time, description)
                                reminders.add(reminder)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
        }
        return reminders
    }

    fun getDummyReminders() : MutableList<Reminder> {
        val reminders: MutableList<Reminder> = mutableListOf()
        val reminder1 = Reminder("1", "1", "Reminder 1", "01/01/2021", "12:00", "Description 1")
        val reminder2 = Reminder("2", "1", "Reminder 2", "01/01/2021", "12:00", "Description 2")
        val reminder3 = Reminder("3", "1", "Reminder 3", "01/01/2021", "12:00", "Description 3")
        val reminder4 = Reminder("4", "1", "Reminder 4", "01/01/2021", "12:00", "Description 4")
        val reminder5 = Reminder("5", "1", "Reminder 5", "01/01/2021", "12:00", "Description 5")
        reminders.add(reminder1)
        reminders.add(reminder2)
        reminders.add(reminder3)
        reminders.add(reminder4)
        reminders.add(reminder5)
        return reminders
    }

}