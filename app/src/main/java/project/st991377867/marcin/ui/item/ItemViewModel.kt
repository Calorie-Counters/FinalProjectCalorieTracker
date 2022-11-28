package project.st991377867.marcin.ui.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import project.st991377867.marcin.data.Item
import project.st991377867.marcin.data.ItemDao

class ItemViewModel(private val itemDao: ItemDao) : ViewModel() {


    fun addNewItem(itemName: String,
                   itemWeight: String,
                   itemQuantity: String,
                   itemCalorie: String,
                   itemDesc: String) {
        val newItem = getNewItemEntry(itemName, itemWeight, itemQuantity, itemCalorie, itemDesc)
        insertItem(newItem)
    }

    private fun insertItem(item: Item) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    fun isEntryValid(itemName: String,
                     itemWeight: String,
                     itemQuantity: String,
                     itemCalorie: String,
                     itemDescription: String,): Boolean {
        if (itemName.isBlank() ||
            itemWeight.isBlank() ||
            itemQuantity.isBlank() ||
            itemCalorie.isBlank() ||
            itemDescription.isBlank()) {
            return false
        }
        return true
    }

    private fun getNewItemEntry(itemName: String,
                                itemWeight: String,
                                itemQuantity: String,
                                itemCalorie: String,
                                itemDesc: String): Item {
        return Item(
            item_name = itemName,
            item_weight = itemWeight.toDouble(),
            item_quantity = itemQuantity.toDouble(),
            item_calorie = itemCalorie.toDouble(),
            item_description = itemDesc
        )
    }


}

class ItemViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ItemViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}