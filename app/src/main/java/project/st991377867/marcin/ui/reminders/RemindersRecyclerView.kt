package project.st991377867.marcin.ui.reminders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.st991377867.marcin.R
import project.st991377867.marcin.data.model.Reminder

class RemindersRecyclerView (private val reminderList: List<Reminder>) : RecyclerView.Adapter<RemindersRecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reminder_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reminderList[position])
    }

    override fun getItemCount(): Int {
        return reminderList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(reminder: Reminder) {
            itemView.findViewById<TextView>(R.id.reminderTitle).text = reminder.title
            itemView.findViewById<TextView>(R.id.reminderDate).text = reminder.date
            itemView.findViewById<TextView>(R.id.reminderTime).text = reminder.time
            itemView.findViewById<TextView>(R.id.reminderDescription).text = reminder.description
        }
    }
}