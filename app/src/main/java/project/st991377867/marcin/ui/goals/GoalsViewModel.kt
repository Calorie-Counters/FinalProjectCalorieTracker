package project.st991377867.marcin.ui.goals

import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import project.st991377867.marcin.data.model.DailyCalorieIntake
import project.st991377867.marcin.data.model.Goal
import java.text.DateFormat
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Calendar

class GoalsViewModel : ViewModel() {
    val fireStoreDatabase = FirebaseFirestore.getInstance();

    val firebaseUserID = FirebaseAuth.getInstance().currentUser?.uid
    val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
    var goal: Goal? = null
    var pastGoals: MutableMap<String, Goal> = mutableMapOf()

    fun setNewGoal(newGoal: Goal){
        /*val nGoal: MutableMap<String, Any> = HashMap()
        nGoal["id"]*/

        val cdate = Calendar.getInstance()
        cdate.set(Calendar.HOUR_OF_DAY, 0)
        cdate.set(Calendar.MINUTE, 0)
        cdate.set(Calendar.SECOND, 0)
        cdate.set(Calendar.MILLISECOND, 0)
        cdate.add(Calendar.DAY_OF_YEAR, -7)
        val gdate = cdate.time.toString() as Timestamp

        if (firebaseUserID != null){
            fireStoreDatabase.collection("goals")
                .document(newGoal.id)
                .update("goal", newGoal.goal,
                    "calories", newGoal.calories,
                    "date", gdate)

        }
    }

    fun fetchGoal(): Goal?{
        if (firebaseUserID != null){
            fireStoreDatabase.collection("goals")
                .whereEqualTo("uid", firebaseUserID)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        var goalDateMarker: Date = Calendar.getInstance().time
                        for (document in it.result){
                            if (goal == null){
                                val id = document.id
                                val uid = document.data.getValue("uid").toString()
                                val goalStatement = document.data.getValue("goal").toString()
                                val calories = document.data.getValue("calories").toString()
                                val date = (document.data.getValue("date") as Timestamp).toDate()
                                goal = Goal(id, uid, goalStatement, calories, dateFormat.format(date))
                                goalDateMarker = date
                            }
                            else {
                                /*val curDate: Date = Date.from()*/

                                // get the most recent date
                                val newDate: Date = (document.data.getValue("date") as Timestamp).toDate()
                                if (newDate.after(goalDateMarker)){
                                    val id = document.id
                                    val uid = document.data.getValue("uid").toString()
                                    val goalStatement = document.data.getValue("goal").toString()
                                    val calories = document.data.getValue("calories").toString()
                                    goal = Goal(id, uid, goalStatement, calories, dateFormat.format(newDate))
                                    goalDateMarker = newDate
                                }
                            }
                        }
                    }
                }
        } else { //tmp data
            val cdate = Calendar.getInstance()
            cdate.set(Calendar.HOUR_OF_DAY, 0)
            cdate.set(Calendar.MINUTE, 0)
            cdate.set(Calendar.SECOND, 0)
            cdate.set(Calendar.MILLISECOND, 0)
            cdate.add(Calendar.DAY_OF_YEAR, -7)
            val gdate = cdate.time

            goal = Goal("TEST", "TEST", "Lose Weight", "1500", dateFormat.format(gdate))

        }
        return goal
    }
}