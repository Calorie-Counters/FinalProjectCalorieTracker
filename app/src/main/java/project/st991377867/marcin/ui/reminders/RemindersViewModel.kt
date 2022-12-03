package project.st991377867.marcin.ui.reminders

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import project.st991377867.marcin.data.model.Reminder

class RemindersViewModel : ViewModel() {

    val TAG = "RemindersViewModel"

    private val fireStoreDatabase = FirebaseFirestore.getInstance()
    private val firebaseUserID = FirebaseAuth.getInstance().currentUser?.uid
    val reminderListLiveData: LiveData<List<Reminder>> = MutableLiveData()
    val reminderLiveData: LiveData<Reminder> = MutableLiveData()
    val reminderDateLiveData: LiveData<String> = MutableLiveData()
    val reminderTimeLiveData: LiveData<String> = MutableLiveData()

    fun requestReminder(id: String) : LiveData<Reminder> {
        val reminderLiveData: MutableLiveData<Reminder> = MutableLiveData()
        if (firebaseUserID != null){
            fireStoreDatabase.collection("reminders")
                .whereEqualTo("uid", firebaseUserID)
                .whereEqualTo("id", id)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (document in it.result){
                            try {
                                val id = document.data.getValue("id") as String
                                val userId = document.data.getValue("uid") as String
                                val title = document.data.getValue("title") as String
                                val date = document.data.getValue("date") as String
                                val time = document.data.getValue("time") as String
                                val description = document.data.getValue("description") as String
                                val reminder = Reminder(id, userId, title, date, time, description)
                                reminderLiveData.value = reminder
                            } catch (e: Exception) {
                                Log.d(TAG, "requestReminder: ${e.message}")
                            }
                        }
                    } else {
                        Log.d(TAG, "requestReminder: ${it.exception}")
                    }
                }
        }
        return reminderLiveData
    }

    fun requestReminders(limit: Int = 50): LiveData<List<Reminder>> {
        val reminderList: MutableList<Reminder> = mutableListOf()
        if (firebaseUserID != null){
            fireStoreDatabase.collection("reminders")
                .whereEqualTo("uid", firebaseUserID)
                .limit(limit.toLong())
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (document in it.result){
                            try {
                                val id = document.data.getValue("id") as String
                                val userId = document.data.getValue("uid") as String
                                val title = document.data.getValue("title") as String
                                val date = document.data.getValue("date") as String
                                val time = document.data.getValue("time") as String
                                val description = document.data.getValue("description") as String
                                val reminder = Reminder(id, userId, title, date, time, description)
                                Log.d(TAG, reminder.toString())
                                reminderList.add(reminder)

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        reminderListLiveData as MutableLiveData
                        reminderListLiveData.value = reminderList
                    }
                }
        }
        Log.d(TAG, "requestReminders: ${reminderListLiveData.value}")
        return reminderListLiveData
    }

    fun updateReminder(reminder: Reminder) {
        val reminderHashMap = reminderToHashMap(reminder)
        if (firebaseUserID != null){
            fireStoreDatabase.collection("reminders")
                .document(reminder.id)
                .set(reminderHashMap)
                .addOnCompleteListener {
                    Log.d(TAG, "Complete updateReminder: ${it.result}")
                }
                .addOnFailureListener {
                    Log.d(TAG, "Failure updateReminder: ${it.message}")
                }
        }
    }

    fun deleteReminder(reminder: Reminder) {
        if (firebaseUserID != null){
            fireStoreDatabase.collection("reminders")
                .document(reminder.id)
                .delete()
                .addOnCompleteListener {
                    Log.d(TAG, "Complete deleteReminder: ${it.result}")
                }
                .addOnFailureListener {
                    Log.d(TAG, "Failure deleteReminder: ${it.message}")
                }
        }
    }

    fun newReminder() : LiveData<Reminder> {
        val id = fireStoreDatabase.collection("reminders").document().id
        val reminder = Reminder(id, firebaseUserID!!, "", "", "", "")
        reminderLiveData as MutableLiveData
        reminderLiveData.value = reminder
        return reminderLiveData
    }

    fun isEntryValid(title: String, date: String, time: String): Boolean {
        return title.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()
    }

    fun setReminderDate(date: String) {
        reminderDateLiveData as MutableLiveData
        reminderDateLiveData.value = date
    }

    fun setReminderTime(time: String) {
        reminderTimeLiveData as MutableLiveData
        reminderTimeLiveData.value = time
    }

    private fun reminderToHashMap(reminder: Reminder): HashMap<String, Any> {
        val reminderHashMap = HashMap<String, Any>()
        reminderHashMap["id"] = reminder.id
        reminderHashMap["uid"] = reminder.uid
        reminderHashMap["title"] = reminder.title
        reminderHashMap["date"] = reminder.date
        reminderHashMap["time"] = reminder.time
        reminderHashMap["description"] = reminder.description
        return reminderHashMap
    }

}