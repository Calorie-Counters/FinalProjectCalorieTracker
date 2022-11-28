package project.st991377867.marcin.ui.diets

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import project.st991377867.marcin.data.model.Diet

class DietsViewModel : ViewModel() {

    val fireStoreDatabase = FirebaseFirestore.getInstance()
    val firebaseUserID = FirebaseAuth.getInstance().currentUser?.uid

    val dietsList: MutableList<Diet> = mutableListOf()

    fun getDiets() : MutableList<Diet> {
        val diets: MutableList<Diet> = mutableListOf()

        if (firebaseUserID != null){
            fireStoreDatabase.collection("diets")
                .whereEqualTo("uid", firebaseUserID)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (document in it.result){
                            try {
                                val id = document.data.getValue("id") as String
                                val title = document.data.getValue("name") as String
                                val description = document.data.getValue("description") as String
                                val image = document.data.getValue("image") as String
                                val type = document.data.getValue("type") as String
                                val calories = document.data.getValue("calories") as String
                                val protein = document.data.getValue("protein") as String
                                val fat = document.data.getValue("fat") as String
                                val carbs = document.data.getValue("carbs") as String
                                val ingredients = document.data.getValue("ingredients") as String
                                val preparation = document.data.getValue("preparation") as String
                                val tips = document.data.getValue("tips") as String
                                val duration = document.data.getValue("duration") as String
                                val diet = Diet(id, title, description, image, type, calories, protein, fat, carbs, ingredients, preparation, tips, duration)
                                diets.add(diet)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
        }
        return diets
    }

    fun getDummyDiets() : MutableList<Diet> {
        val diets: MutableList<Diet> = mutableListOf()
        val diet1 =
            Diet("1", "Diet 1", "Description 1", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com%2Fpin%2F397000000000000000%2F&psig=AOvVaw3Z0Z0Z0Z0Z0Z0Z0Z0Z0Z0Z&ust=1610000000000000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJjZ0Z0Z0Z0Z0Z0ZSAAAAAAdAAAAABAD", "type", "calories", "protein", "fat", "carbs", "ingredients", "preparation", "tips", "duration")
        val diet2 =
            Diet("2", "Diet 2", "Description 2", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com%2Fpin%2F397000000000000000%2F&psig=AOvVaw3Z0Z0Z0Z0Z0Z0Z0Z0Z0Z0Z&ust=1610000000000000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJjZ0Z0Z0Z0Z0Z0ZSAAAAAAdAAAAABAD", "type", "calories", "protein", "fat", "carbs", "ingredients", "preparation", "tips", "duration")
        val diet3 =
            Diet("3", "Diet 3", "Description 3", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pinterest.com%2Fpin%2F397000000000000000%2F&psig=AOvVaw3Z0Z0Z0Z0Z0Z0Z0Z0Z0Z0Z&ust=1610000000000000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJjZ0Z0Z0Z0Z0Z0ZSAAAAAAdAAAAABAD", "type", "calories", "protein", "fat", "carbs", "ingredients", "preparation", "tips", "duration")

        diets.add(diet1)
        diets.add(diet2)
        diets.add(diet3)

        return diets
    }
}