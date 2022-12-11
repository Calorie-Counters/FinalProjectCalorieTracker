package project.st991377867.marcin.ui.item


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import project.st991377867.marcin.data.model.Item
import project.st991377867.marcin.databinding.FragmentItemBinding


class ItemFragment : Fragment() {

    private val navigationArgs: ItemFragmentArgs by navArgs()
    private lateinit var binding: FragmentItemBinding
    private lateinit var item: Item

    private val viewModel by activityViewModels<ItemViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.itemId

        if (id != "-1") {
            binding.btnSave.visibility = View.GONE
            viewModel.requestItem(id).observe(viewLifecycleOwner) {
                binding.item = it
            }
            binding.btnUpdate.setOnClickListener {
                updateItem()
            }
            binding.btnDelete.setOnClickListener {
                showConfirmationDialog()
            }
        } else {
            viewModel.newItem().observe(viewLifecycleOwner) {
                item = it
                binding.item = item
            }
            binding.btnDelete.visibility = View.GONE
            binding.btnUpdate.visibility = View.GONE
            binding.btnSave.setOnClickListener {
                updateItem()
            }
        }

    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage("Are you sure you want to delete this item?")
            .setCancelable(false)
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Delete") { _, _ ->
                deleteItem()
            }
            .show()
    }

    private fun deleteItem() {
        viewModel.deleteItem(binding.item!!)
        findNavController().navigateUp()
    }


    private fun updateItem() {
        if (binding.item != null) {
            val item = binding.item!!
            Log.d("Log", item.toString())
            viewModel.updateItem(item)
            val action = ItemFragmentDirections.actionItemFragmentToNavHome()
            findNavController().navigate(action)
        }
    }

}