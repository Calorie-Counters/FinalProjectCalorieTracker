package project.st991377867.marcin.ui.diets

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import project.st991377867.marcin.data.model.Diet
import project.st991377867.marcin.data.model.Reminder

class DietsViewModel : ViewModel() {

    val TAG = "DietsViewModel"

    private val fireStoreDatabase = FirebaseFirestore.getInstance()
    private val firebaseUserID = FirebaseAuth.getInstance().currentUser?.uid
    val dietListLiveData: LiveData<List<Diet>> = MutableLiveData()
    val dietLiveData: LiveData<Diet> = MutableLiveData()

    fun requestDiet(id: String) : LiveData<Diet> {
        val dietLiveData: MutableLiveData<Diet> = MutableLiveData()
        if (firebaseUserID != null){
            fireStoreDatabase.collection("diets")
                .get()
                .addOnCompleteListener {
                    Log.d(TAG, "requestDiet: ${it.result}")
                    if (it.isSuccessful) {
                        for (document in it.result){
                            try {
                                val id = document.data.getValue("id") as String
                                val name = document.data.getValue("name") as String
                                val description = document.data.getValue("description") as String
                                val type = document.data.getValue("type") as String
                                val calories = document.data.getValue("calories") as String
                                val ingredients = document.data.getValue("ingredients") as String
                                val instructions = document.data.getValue("instructions") as String
                                val duration = document.data.getValue("duration") as String
                                val diet = Diet(id, name, description, type, calories, ingredients, instructions, duration)
                                dietLiveData.value = diet
                            } catch (e: Exception) {
                                Log.d(TAG, "requestDiet: ${e.message}")
                            }
                        }
                    } else {
                        Log.d(TAG, "requestDiet: ${it.exception}")
                    }
                }
        }
        return dietLiveData
    }

    fun requestDiets(limit: Int = 50): LiveData<List<Diet>> {
        clearList()
        val dietList: MutableList<Diet> = mutableListOf()
        if (firebaseUserID != null){
            fireStoreDatabase.collection("diets")
                .limit(limit.toLong())
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (document in it.result){
                            try {
                                val id = document.data.getValue("id") as String
                                val name = document.data.getValue("name") as String
                                val description = document.data.getValue("description") as String
                                val type = document.data.getValue("type") as String
                                val calories = document.data.getValue("calories") as String
                                val ingredients = document.data.getValue("ingredients") as String
                                val instructions = document.data.getValue("instructions") as String
                                val duration = document.data.getValue("duration") as String
                                val diet = Diet(id, name, description, type, calories, ingredients, instructions, duration)
                                dietList.add(diet)
                            } catch (e: Exception) {
                                Log.d(TAG, "requestDiets: ${e.message}")
                            }
                        }
                        (dietListLiveData as MutableLiveData).value = dietList
                    } else {
                        Log.d(TAG, "requestDiets: ${it.exception}")
                    }
                }
        }
        return dietListLiveData
    }

    fun updateDiet(diet: Diet) {
        val dietHashMap = dietToHashMap(diet)
        if (firebaseUserID != null) {
            fireStoreDatabase.collection("diets")
                .document(diet.id)
                .set(dietHashMap)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d(TAG, "updateDiet: success")
                    } else {
                        Log.d(TAG, "updateDiet: ${it.exception}")
                    }
                }
        }
    }

    fun deleteDiet(diet: Diet) {
        if (firebaseUserID != null) {
            fireStoreDatabase.collection("diets")
                .document(diet.id)
                .delete()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d(TAG, "deleteDiet: success")
                    } else {
                        Log.d(TAG, "deleteDiet: ${it.exception}")
                    }
                }
        }
    }

    fun newDiet() : LiveData<Diet> {
        val id = fireStoreDatabase.collection("diets").document().id
        val diet = Diet(id, "", "", "", "", "", "", "")
        (dietLiveData as MutableLiveData).value = diet
        return dietLiveData
    }

    private fun clearList() {
        (dietListLiveData as MutableLiveData).value = listOf()
    }

    private fun dietToHashMap(diet: Diet): HashMap<String, Any> {
        val dietHashMap = HashMap<String, Any>()
        dietHashMap["id"] = diet.id
        dietHashMap["name"] = diet.name
        dietHashMap["description"] = diet.description
        dietHashMap["type"] = diet.type
        dietHashMap["calories"] = diet.calories
        dietHashMap["ingredients"] = diet.ingredients
        dietHashMap["instructions"] = diet.instructions
        dietHashMap["duration"] = diet.duration
        return dietHashMap
    }

}