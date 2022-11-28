package project.st991377867.marcin.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val item_name: String,
    @ColumnInfo(name = "weight")
    val item_weight: Double,
    @ColumnInfo(name = "quantity")
    val item_quantity: Double,
    @ColumnInfo(name = "calorie")
    val item_calorie: Double,
    @ColumnInfo(name = "description")
    val item_description: String
)