package project.st991377867.marcin.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.st991377867.marcin.data.model.DailyCalorieIntake
import java.text.DateFormat
import java.text.SimpleDateFormat

class HistoryRecyclerView (private val recordList: List<DailyCalorieIntake>) : RecyclerView.Adapter <HistoryRecyclerView.MyViewHolder>(){

    val dateShortDateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
    val dateLongFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val dateText: TextView = view.findViewById(project.st991377867.marcin.R.id.recordDate)
        val calorieText: TextView = view.findViewById(project.st991377867.marcin.R.id.totalCalories)
        val itemList: TextView = view.findViewById(project.st991377867.marcin.R.id.listItems)
        val expandImage: ImageView = view.findViewById(project.st991377867.marcin.R.id.expandImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val historyCardView = LayoutInflater.from(parent.context)
            .inflate(project.st991377867.marcin.R.layout.history_record_cardview, parent, false)

        return MyViewHolder(historyCardView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = recordList[position]

        holder.dateText.text = dateShortDateFormat.format(recordList[position].date)
        holder.calorieText.text = recordList[position].totalCalories.toString()
        if (recordList[position].items.size < 1){
            holder.itemList.text = "No records present for this date"
        } else {
            val displayText: StringBuffer = StringBuffer()
            displayText.append(String.format("\n%-32s\t%-20s\t%-10s\t%-10s\n","Date","Item Name", "Quantity", "Calories"))
            displayText.append(String.format("%100s\n","").replace(" ", "-"))
            for (i in 0 .. recordList[position].items.size - 1){
                val itemName: String
                if (recordList[position].items[i].itemName.length > 20){
                    itemName = recordList[position].items[i].itemName.substring(0,17) + "..."
                } else {
                    itemName = recordList[position].items[i].itemName
                }

                displayText.append(String.format("%-25s\t%-25s\t%-12s\t%-10s",
                    dateLongFormat.format(recordList[position].items[i].date),
                    itemName,
                    String.format("%.1f",recordList[position].items[i].itemQuantity),
                    String.format("%d",recordList[position].items[i].kCal)))
                if (i != recordList[position].items.size - 1)
                    displayText.append("\n")

            }
            holder.itemList.text = displayText
        }

        holder.expandImage.setOnClickListener {
            if (holder.itemList.visibility == View.VISIBLE){
                holder.itemList.visibility = View.GONE
            } else {
                holder.itemList.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount() = recordList.size
}