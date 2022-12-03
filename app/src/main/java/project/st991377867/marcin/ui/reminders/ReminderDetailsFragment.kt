package project.st991377867.marcin.ui.reminders

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
import project.st991377867.marcin.data.model.Reminder
import project.st991377867.marcin.databinding.FragmentReminderDetailsBinding

class ReminderDetailsFragment : Fragment() {

    val TAG = "ReminderDetailsFragment"

    private val navigationArgs: ReminderDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentReminderDetailsBinding
    private lateinit var reminder: Reminder

    // firestore database
    private val viewModel by activityViewModels<RemindersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReminderDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.reminderId

        if (id != "-1") {
            binding.buttonAdd.visibility = View.GONE
            viewModel.requestReminder(id).observe(viewLifecycleOwner) {
                binding.reminder = it
            }
            binding.buttonSave.setOnClickListener {
                updateReminder()
            }
            binding.buttonDelete.setOnClickListener {
                showConfirmationDialog()
            }
        } else {
            viewModel.newReminder().observe(viewLifecycleOwner) {
                reminder = it
                binding.reminder = reminder
            }
            binding.reminderDetailsEditButtons.visibility = View.GONE
            binding.buttonAdd.setOnClickListener {
                updateReminder()
            }
        }
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage("Are you sure you want to delete this reminder?")
            .setCancelable(false)
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Delete") { _, _ ->
                deleteReminder()
            }
            .show()
    }

    private fun deleteReminder() {
        viewModel.deleteReminder(binding.reminder!!)
        findNavController().navigateUp()
    }

    private fun isEntryValid(): Boolean {
        val reminder = binding.reminder!!
        return viewModel.isEntryValid(
            reminder.title,
            reminder.date,
            reminder.time,
            reminder.description
        )
        return false
    }

    private fun updateReminder() {
        if (binding.reminder != null && isEntryValid()) {
            val reminder = binding.reminder!!
            viewModel.updateReminder(reminder)
            val action = ReminderDetailsFragmentDirections.actionAddItemFragmentToItemListFragment()
            findNavController().navigate(action)
        }
    }

}