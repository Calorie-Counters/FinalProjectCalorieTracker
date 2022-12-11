package project.st991377867.marcin.ui.item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import project.st991377867.marcin.data.model.Item


import project.st991377867.marcin.ui.login.LoginFragment.Companion.TAG
import java.util.*
import kotlin.collections.HashMap


class ItemViewModel : ViewModel() {


    private val fireStoreDatabase = FirebaseFirestore.getInstance()
    private val firebaseUserID = FirebaseAuth.getInstance().currentUser?.uid

    val itemListLiveData: LiveData<List<Item>> = MutableLiveData()
    val itemLiveData: LiveData<Item> = MutableLiveData()




    fun requestItem(id: String) : LiveData<Item> {
        val itemLiveData: MutableLiveData<Item> = MutableLiveData()

        if (firebaseUserID != null){
            fireStoreDatabase.collection("items")
                .whereEqualTo("uid", firebaseUserID)
                .whereEqualTo("id", id)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (document in it.result){
                            try {
                                val id = document.data.getValue("id") as String
                                val uid = document.data.getValue("uid") as String
                                val itemName = document.data.getValue("itemName") as String
                                val itemWeight = document.data.getValue("itemWeight") as String
                                val itemQuantity = document.data.getValue("itemQuantity") as String
                                val itemCalorie = document.data.getValue("itemCalorie") as String
                                val itemDescription = document.data.getValue("itemDescription") as String

                                val item = Item(id, uid, itemName, itemWeight, itemQuantity, itemCalorie, itemDescription)
                                itemLiveData.value = item

                            } catch (e: Exception) {
                                Log.d(TAG, "requestItem: ${e.message}")
                            }
                        }
                    } else {
                        Log.d(TAG, "requestItem: ${it.exception}")
                    }
                }
        }
        return itemLiveData
    }

    fun requestItems(limit: Int = 50): LiveData<List<Item>> {
        val itemList: MutableList<Item> = mutableListOf()
        if (firebaseUserID != null){
            fireStoreDatabase.collection("items")
                .whereEqualTo("uid", firebaseUserID)
                .limit(limit.toLong())
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (document in it.result){
                            try {
                                val id = document.data.getValue("id") as String
                                val uid = document.data.getValue("uid") as String
                                val itemName = document.data.getValue("itemName") as String
                                val itemWeight = document.data.getValue("itemWeight") as String
                                val itemQuantity = document.data.getValue("itemQuantity") as String
                                val itemCalorie = document.data.getValue("itemCalorie") as String
                                val itemDescription = document.data.getValue("itemDescription") as String

                                val item = Item(id, uid, itemName, itemWeight, itemQuantity, itemCalorie, itemDescription)
                                itemList.add(item)

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        itemListLiveData as MutableLiveData
                        itemListLiveData.value = itemList
                    }
                }
        }
        return itemListLiveData
    }


    fun newItem() : LiveData<Item> {
        val id = fireStoreDatabase.collection("items").document().id
        val item = Item(id,firebaseUserID!!,"","", "", "", "")
        itemLiveData as MutableLiveData
        itemLiveData.value = item
        return itemLiveData
    }

    fun updateItem(item: Item) {
        val itemHashMap = itemToHashMap(item)
        if (firebaseUserID != null){
            fireStoreDatabase.collection("items")
                .document(item.id)
                .set(itemHashMap)
                .addOnCompleteListener {
                    Log.d(TAG, "Complete updateItem: ${it.result}")
                }
                .addOnFailureListener {
                    Log.d(TAG, "Failure updateItem: ${it.message}")
                }
        }
    }

    fun deleteItem(item: Item) {
        if (firebaseUserID != null){
            fireStoreDatabase.collection("items")
                .document(item.id)
                .delete()
                .addOnCompleteListener {
                    Log.d(TAG, "Complete deleteItem: ${it.result}")
                }
                .addOnFailureListener {
                    Log.d(TAG, "Failure deleteItem: ${it.message}")
                }
        }
    }

    private fun itemToHashMap(item: Item): HashMap<String, Any> {
        val currentDateTime = Calendar.getInstance().time
        val itemHashMap = HashMap<String, Any>()
        itemHashMap["id"] = item.id
        itemHashMap["uid"] = item.uid
        itemHashMap["itemName"] = item.item_name
        itemHashMap["itemWeight"] = item.item_weight
        itemHashMap["itemQuantity"] = item.item_quantity
        itemHashMap["itemCalorie"] = item.item_calorie
        itemHashMap["itemDescription"] = item.item_description
        itemHashMap["timestamp"] = currentDateTime
        return itemHashMap
    }

}
