package project.st991377867.marcin.data.model

import java.util.Date

class ItemEntry(
    _date: Date,
    _itemName: String,
    _itemQuantity: Int,
    _kCal: Int
)
{
    val date: Date
    val itemName: String
    val itemQuantity: Int
    val kCal: Int

    init {
        date = _date
        itemName = _itemName
        itemQuantity = _itemQuantity
        kCal = _kCal
    }
}
