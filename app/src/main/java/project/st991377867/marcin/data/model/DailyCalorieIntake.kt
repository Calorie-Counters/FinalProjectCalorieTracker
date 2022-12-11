package project.st991377867.marcin.data.model

import java.util.Date
import kotlin.math.roundToInt

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
    fun addItem(entryDate: Date, name: String, quantity: Double, kcal: Int){
        items.add(ItemEntry(entryDate, name, quantity, kcal))

        totalCalories += (quantity * kcal).roundToInt()
    }
}