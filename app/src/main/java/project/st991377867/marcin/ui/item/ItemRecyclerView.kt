//package project.st991377867.marcin.ui.item
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import project.st991377867.marcin.R
//import project.st991377867.marcin.data.model.Item
//
//class ItemRecyclerView (private val itemList: List<Item>) : RecyclerView.Adapter<ItemRecyclerView.ViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(itemList[position])
//    }
//
//    override fun getItemCount(): Int {
//        return itemList.size
//    }
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        fun bind(item: Item) {
//            itemView.findViewById<TextView>(R.id.item_name).text = item.item_name
//            itemView.findViewById<TextView>(R.id.item_quantity).text = item.item_quantity
//            itemView.findViewById<TextView>(R.id.item_weight).text = item.item_weight
//            itemView.findViewById<TextView>(R.id.item_calorie).text = item.item_calorie
//            itemView.findViewById<TextView>(R.id.item_description).text = item.item_description
//        }
//    }
//}