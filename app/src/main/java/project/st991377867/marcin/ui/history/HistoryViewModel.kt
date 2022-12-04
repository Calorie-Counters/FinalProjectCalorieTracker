package project.st991377867.marcin.ui.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import project.st991377867.marcin.data.FirebaseUserLiveData
import project.st991377867.marcin.data.model.DailyCalorieIntake
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import kotlin.random.Random
import kotlin.random.nextInt

class HistoryViewModel : ViewModel() {

    val fireStoreDatabase = FirebaseFirestore.getInstance();
    val firebaseUserID = FirebaseAuth.getInstance().currentUser?.uid
    val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")

    val historyList: MutableLiveData<List<DailyCalorieIntake>> = MutableLiveData()


    fun recentHistory(duration: Int) :MutableMap<String, DailyCalorieIntake> {

        var dailyCalorieMap: MutableMap<String, DailyCalorieIntake> = mutableMapOf()

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

        for (i in 1 .. duration){
            val tmpCal = calendarDateNow
            tmpCal.add(Calendar.DAY_OF_YEAR, -1)
            dailyCalorieMap.put("${dateFormat.format(tmpCal.time)}", DailyCalorieIntake(tmpCal.time))
        }

        if (firebaseUserID != null){
            Log.d("HistoryFragment", "UID: ${firebaseUserID}")
            fireStoreDatabase.collection("items")// replace with w/e the food collection is called
                .whereEqualTo("uid", firebaseUserID)
                .whereLessThan("timestamp", now)
                .whereGreaterThanOrEqualTo("timestamp", timeFrameDate)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("HistoryFragment", "num results: ${it.result.size()}")
                        for (document in it.result){
                            try {
                                val timeStamp = document.data.getValue("timestamp") as com.google.firebase.Timestamp
                                val recordDate = timeStamp.toDate()
                                // check to make sure the value is in the map
                                if (dailyCalorieMap.containsKey(dateFormat.format(recordDate))){
                                    Log.d("HistoryFragment", "record date in map")
                                    dailyCalorieMap[dateFormat.format(recordDate)]!!
                                        .addItem(recordDate,
                                            document.data.getValue("itemName").toString(),
                                            document.data.getValue("itemQuantity").toString().toDouble(),
                                            document.data.getValue("itemCalorie").toString().toInt())
                                }
                            }
                            catch (e: Exception){
                                Log.e("HistoryFragment", e.printStackTrace().toString())
                                //break;
                            }
                        }
                    }
                }
        }
        return dailyCalorieMap
    }

}