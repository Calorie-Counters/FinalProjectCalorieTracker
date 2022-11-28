package project.st991377867.marcin.ui.item

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import project.st991377867.marcin.data.Item
import project.st991377867.marcin.databinding.FragmentItemBinding

class ItemFragment : Fragment() {

    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!

    lateinit var item: Item

    companion object {
        fun newInstance() = ItemFragment()
    }

    private lateinit var viewModel: ItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_item, container, false)
        _binding = FragmentItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        // TODO: Use the ViewModel
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveAction.setOnClickListener {
            addNewItem()
        }
    }

    private fun addNewItem() {
//        viewModel.addNewItem(
//            binding.itemName.text.toString(),
//            binding.itemWeight.text.toString(),
//            binding.itemQuantity.text.toString(),
//            binding.itemCalorie.text.toString(),
//            binding.itemDescription.text.toString()
//        )
        val action = ItemFragmentDirections.actionItemFragmentToNavHome()
        findNavController().navigate(action)
    }
}