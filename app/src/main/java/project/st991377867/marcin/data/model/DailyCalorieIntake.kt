package project.st991377867.marcin.data.model

import java.util.Date

class DailyCalorieIntake(
    recordDate: Date
)
{
    var totalCalories: Int
    val date: Date
    val items: MutableList<ItemEntry>

    init {
        totalCalories = 0
        date = recordDate
        items = mutableListOf()
    }
    fun addItem(entryDate: Date, name: String, quantity: Int, kcal: Int){
        items.add(ItemEntry(entryDate, name, quantity, kcal))
        totalCalories += kcal
    }
}