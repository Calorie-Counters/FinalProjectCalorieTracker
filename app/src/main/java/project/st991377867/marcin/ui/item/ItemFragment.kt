package project.st991377867.marcin.ui.item

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
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

    //private lateinit var viewModel: ItemViewModel

    private val viewModel: ItemViewModel by activityViewModels {
        ItemViewModelFactory(
            (activity?.application as ItemApplication).database.itemDao()
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_item, container, false)
        _binding = FragmentItemBinding.inflate(inflater, container, false)
        return binding.root
    }




    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.itemName.text.toString(),
            binding.itemWeight.text.toString(),
            binding.itemQuantity.text.toString(),
            binding.itemCalorie.text.toString(),
            binding.itemDescription.text.toString()
        )
    }

    private fun addNewItem() {
        if (isEntryValid()) {
            viewModel.addNewItem(
                binding.itemName.text.toString(),
                binding.itemWeight.text.toString(),
                binding.itemQuantity.text.toString(),
                binding.itemCalorie.text.toString(),
                binding.itemDescription.text.toString()
            )
            val action = ItemFragmentDirections.actionItemFragmentToNavHome()
            findNavController().navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveAction.setOnClickListener {
            addNewItem()
        }
    }




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}