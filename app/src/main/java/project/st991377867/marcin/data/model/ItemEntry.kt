package project.st991377867.marcin.data.model

import java.util.Date

class ItemEntry(
    _date: Date,
    _itemName: String,
    _itemQuantity: Double,
    _kCal: Int
)
{
    val date: Date
    val itemName: String
    val itemQuantity: Double
    val kCal: Int

    init {
        date = _date
        itemName = _itemName
        itemQuantity = _itemQuantity
        kCal = _kCal
    }
}
