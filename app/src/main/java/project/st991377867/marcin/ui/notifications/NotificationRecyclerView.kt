package project.st991377867.marcin.ui.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.st991377867.marcin.R
import project.st991377867.marcin.data.model.Notification

class NotificationRecyclerView (private val notificationList : List<Notification>) : RecyclerView.Adapter<NotificationRecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notificationList[position])
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(notification: Notification) {
            itemView.findViewById<TextView>(R.id.notificationTitle).text = notification.title
            itemView.findViewById<TextView>(R.id.notificationDate).text = notification.date
            itemView.findViewById<TextView>(R.id.notificationTime).text = notification.time
            itemView.findViewById<TextView>(R.id.notificationDescription).text = notification.description
        }
    }
}