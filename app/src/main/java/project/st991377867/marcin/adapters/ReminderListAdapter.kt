package project.st991377867.marcin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import project.st991377867.marcin.data.model.Reminder
import project.st991377867.marcin.databinding.ReminderItemBinding

class ReminderListAdapter(private val onReminderClicked: (Reminder) -> Unit) :
    ListAdapter<Reminder, ReminderListAdapter.ReminderViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        return ReminderViewHolder(
            ReminderItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onReminderClicked(current)
        }
        holder.bind(current)
    }

    class ReminderViewHolder(private var binding: ReminderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reminder: Reminder) {
            binding.apply {
                reminderTitle.text = reminder.title
                reminderDescription.text = reminder.description
                reminderDate.text = reminder.date
                reminderTime.text = reminder.time
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Reminder>() {
            override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}