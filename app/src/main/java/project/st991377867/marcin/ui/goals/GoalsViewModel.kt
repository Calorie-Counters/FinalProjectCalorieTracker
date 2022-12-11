package project.st991377867.marcin.ui.goals

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.tasks.await
import project.st991377867.marcin.data.model.DailyCalorieIntake
import project.st991377867.marcin.data.model.Goal
import java.sql.Time
import java.text.DateFormat
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.math.roundToInt

class GoalsViewModel : ViewModel() {
    val fireStoreDatabase = FirebaseFirestore.getInstance();

    val firebaseUserID = FirebaseAuth.getInstance().currentUser?.uid
    val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
    var goal: Goal? = null
    //var pastGoals: MutableMap<String, Goal> = mutableMapOf()

    fun setNewGoal(goalStatement: String, goalCalories: Int){
        /*val nGoal: MutableMap<String, Any> = HashMap()
        nGoal["id"]*/

        val cdate = Calendar.getInstance()
        cdate.set(Calendar.HOUR_OF_DAY, 0)
        cdate.set(Calendar.MINUTE, 0)
        cdate.set(Calendar.SECOND, 0)
        cdate.set(Calendar.MILLISECOND, 0)
        val gdate: Date = cdate.time

        if (firebaseUserID != null){
            if (goal != null){

                val newGoal: Goal = Goal(goal!!.id, firebaseUserID, goalStatement, goalCalories.toString(), gdate.toString())
                fireStoreDatabase.collection("goals")
                    .document(newGoal.id)
                    .update("goal", newGoal.goal,
                        "calories", newGoal.calories,
                        "timestamp", gdate)
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            Log.d("GoalsFragment", "Goal Set/Updated")
                        }
                    }
            } else { // new goal
                val newGoal: MutableMap<String, Any> = HashMap()
                newGoal["uid"] = firebaseUserID
                newGoal["timestamp"] = gdate
                newGoal["goal"] = goalStatement
                newGoal["calories"] = goalCalories

                fireStoreDatabase.collection("goals")
                    .add(newGoal)
                    .addOnSuccessListener {
                        Log.d("GoalsFragment", "Added document with ID ${it.id}")
                    }
                    .addOnFailureListener { exception ->
                        Log.w("GoalsFragment", "Error adding document $exception")
                    }
            }

        }
    }

    suspend fun deleteGoal(){
        if (goal != null){
            Log.d("GoalsFragment", "id: ${goal?.id}")
            fireStoreDatabase.collection("goals")
                .document(goal!!.id)
                .delete()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("GoalsFragment", "Goal Deleted")
                    }
                }
                .await()
        }
    }


    suspend fun getDailyCalorie() : Int {
        var c: Int = 0
        val cdate = Calendar.getInstance()
        cdate.set(Calendar.HOUR_OF_DAY, 0)
        cdate.set(Calendar.MINUTE, 0)
        cdate.set(Calendar.SECOND, 0)
        cdate.set(Calendar.MILLISECOND, 0)
        val today: Date = cdate.time

        val cdate2 = Calendar.getInstance()
        cdate2.set(Calendar.HOUR_OF_DAY, 0)
        cdate2.set(Calendar.MINUTE, 0)
        cdate2.set(Calendar.SECOND, 0)
        cdate2.set(Calendar.MILLISECOND, 0)
        cdate2.add(Calendar.DAY_OF_YEAR, 1)
        val tomorrow: Date = cdate2.time

        if (firebaseUserID != null){
            fireStoreDatabase.collection("items")
                .whereEqualTo("uid", firebaseUserID)
                .whereGreaterThanOrEqualTo("timestamp", today)
                .whereLessThan("timestamp", tomorrow)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        for (document in it.result){
                            try {
                                val calorie: Int = document.data.getValue("itemCalorie").toString().toInt()
                                val quantity: Double = document.data.getValue("itemQuantity").toString().toDouble()

                                val amount: Int = (calorie * quantity).roundToInt()

                                c += amount
                            } catch (e: Exception){
                                Log.d("GoalsFragment", "${e.message}")
                            }
                        }
                    }
                }
                .await()
        }

        return c
    }


    suspend fun fetchGoal(): Goal?{
        goal = null
        if (firebaseUserID != null){
            //val goal: Goal? = fetchDocs()
            Log.d("GoalsFragment", "goal: ${goal?.id} Statement: ${goal?.goal}")
            fireStoreDatabase.collection("goals")
                .whereEqualTo("uid", firebaseUserID)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        var goalDateMarker: Date = Calendar.getInstance().time
                        for (document in it.result){
                                val id = document.id
                                val uid = document.data.getValue("uid").toString()
                                val goalStatement = document.data.getValue("goal").toString()
                                val calories = document.data.getValue("calories").toString()
                                val date = (document.data.getValue("timestamp") as Timestamp).toDate()
                                goal = Goal(id, uid, goalStatement, calories, dateFormat.format(date))
                                Log.d("GoalsFragment", "goal: ${id} Statement: ${goalStatement}")
                        }
                    }
                }
                .await()
        }
        return goal
    }
}