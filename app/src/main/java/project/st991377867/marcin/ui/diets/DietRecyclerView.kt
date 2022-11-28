package project.st991377867.marcin.ui.diets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.st991377867.marcin.R
import project.st991377867.marcin.data.model.Diet
import project.st991377867.marcin.ui.notifications.NotificationRecyclerView

class DietRecyclerView (private val dietList: List<Diet>) : RecyclerView.Adapter<DietRecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.diet_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dietList[position])
    }

    override fun getItemCount(): Int {
        return dietList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(diet: Diet) {
            itemView.findViewById<TextView>(R.id.dietName).text = diet.name
            itemView.findViewById<TextView>(R.id.dietType).text = diet.type
            itemView.findViewById<TextView>(R.id.dietDuration).text = diet.duration
            itemView.findViewById<TextView>(R.id.dietDescription).text = diet.description
        }
    }
}