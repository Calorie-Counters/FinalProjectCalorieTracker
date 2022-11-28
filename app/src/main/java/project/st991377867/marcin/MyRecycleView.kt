package project.st991377867.marcin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MyRecyclerView (private val sampleList: List <ListItem>): RecyclerView.Adapter <MyRecyclerView.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val item_name: TextView = itemView.findViewById(R.id.item_name)
        val item_weight: TextView = itemView.findViewById(R.id.item_weight)
        val item_quantity: TextView = itemView.findViewById(R.id.item_quantity)
        val item_calorie: TextView = itemView.findViewById(R.id.item_calorie)
        val item_description: TextView = itemView.findViewById(R.id.item_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
            parent, false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount() = sampleList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = sampleList [position]


    }


}