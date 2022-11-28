package project.st991377867.marcin.ui.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import project.st991377867.marcin.data.Item
import project.st991377867.marcin.data.ItemDao

class ItemViewModel : ViewModel() {
//    class ItemViewModel(private val itemDao: ItemDao) : ViewModel() {
    // TODO: Implement the ViewModel
//
//    fun addNewItem(itemName: String,
//                   itemWeight: String,
//                   itemQuantity: String,
//                   itemCalorie: String,
//                   itemDesc: String) {
//        val newItem = getNewItemEntry(itemName, itemWeight, itemQuantity, itemCalorie, itemDesc)
//        insertItem(newItem)
//    }
//
//    private fun insertItem(item: Item) {
//        viewModelScope.launch {
//            itemDao.insert(item)
//        }
//    }
//    private fun getNewItemEntry(itemName: String,
//                                itemWeight: String,
//                                itemQuantity: String,
//                                itemCalorie: String,
//                                itemDesc: String): Item {
//        return Item(
//            item_name = itemName,
//            item_weight = itemWeight.toDouble(),
//            item_quantity = itemQuantity.toDouble(),
//            item_calorie = itemCalorie.toDouble(),
//            item_description = itemDesc
//        )
//    }
}