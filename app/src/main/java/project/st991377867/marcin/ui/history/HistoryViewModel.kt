package project.st991377867.marcin.ui.history

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import project.st991377867.marcin.data.FirebaseUserLiveData
import java.util.Calendar
import java.util.Date
import kotlin.random.Random

class HistoryViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    val fireStoreDatabase = FirebaseFirestore.getInstance();

    val firebaseUserID = FirebaseAuth.getInstance().currentUser?.uid

    fun RecentHistory(duration: Int) :StringBuffer {
        val result: StringBuffer = StringBuffer()

        val calendarDateNow = Calendar.getInstance()
        calendarDateNow.set(Calendar.HOUR_OF_DAY, 0)
        calendarDateNow.set(Calendar.MINUTE, 0)
        calendarDateNow.set(Calendar.SECOND, 0)
        calendarDateNow.set(Calendar.MILLISECOND, 0)

        val now: Date = calendarDateNow.time

        val calendarTimeFrame = Calendar.getInstance()
        calendarTimeFrame.time = now
        calendarTimeFrame.add(Calendar.DAY_OF_YEAR, -duration)
        val timeFrameDate: Date = calendarTimeFrame.time

        if (firebaseUserID != null){
            fireStoreDatabase.collection("items")// replace with w/e the food collection is called
                .whereEqualTo("uid", firebaseUserID)
                .whereLessThan("timestamp", now)
                .whereGreaterThanOrEqualTo("timestamp", timeFrameDate)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (document in it.result){
                            try {
                                result.append(document.data.getValue("timestamp")).append("|")
                                result.append(document.data.getValue("itemName")).append("|")
                                result.append(document.data.getValue("quantity")).append("|")
                                result.append(document.data.getValue("kcal")).append("\n")
                            } catch (e: NoSuchElementException){
                                result.append("\n")
                            }
                        }
                    }
                }
        } else { //tmp
            for (i in 1 .. duration){
                var tempCalendar = Calendar.getInstance()
                tempCalendar.add(Calendar.DAY_OF_YEAR, -i)
                result.append("${tempCalendar.time}|item${i}|x${i}|${Random.nextInt(0, 500)}\n")
            }
        }
        return result;
    }

}