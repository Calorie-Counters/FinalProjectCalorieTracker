package project.st991377867.marcin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import project.st991377867.marcin.data.model.Diet
import project.st991377867.marcin.databinding.DietItemBinding

class DietListAdapter(private val onDietClicked: (Diet) -> Unit) :
    ListAdapter<Diet, DietListAdapter.DietViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietViewHolder {
        return DietViewHolder(
            DietItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: DietViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onDietClicked(current)
        }
        holder.bind(current)
    }

    class DietViewHolder(private var binding: DietItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(diet: Diet) {
            binding.apply {
                dietName.text = diet.name
                dietDescription.text = diet.description
                dietType.text = diet.type
                dietCalories.text = diet.calories
                dietDuration.text = diet.duration
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Diet>() {
            override fun areItemsTheSame(oldItem: Diet, newItem: Diet): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Diet, newItem: Diet): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}