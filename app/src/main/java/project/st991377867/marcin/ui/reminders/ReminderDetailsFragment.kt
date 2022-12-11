package project.st991377867.marcin.ui.reminders

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import project.st991377867.marcin.data.model.Reminder
import project.st991377867.marcin.databinding.FragmentReminderDetailsBinding
import java.util.*

class ReminderDetailsFragment : Fragment() {

    val TAG = "ReminderDetailsFragment"

    private val navigationArgs: ReminderDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentReminderDetailsBinding
    private lateinit var reminder: Reminder

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

        // If a reminder id was passed as an argument
        if (id != "-1") {
            // Hide the "Add" button
            binding.buttonAdd.visibility = View.GONE
            // Request the reminder with the given id and observe the result
            viewModel.requestReminder(id).observe(viewLifecycleOwner) {
                // Bind the reminder to the layout
                binding.reminder = it
            }
            // Set up the "Save" button
            binding.buttonSave.setOnClickListener {
                updateReminder()
            }
            // Set up the "Delete" button
            binding.buttonDelete.setOnClickListener {
                showConfirmationDialog()
            }
        } else {
            // Request a new reminder and observe the result
            viewModel.newReminder().observe(viewLifecycleOwner) {
                reminder = it
                // Bind the reminder to the layout
                binding.reminder = reminder
            }
            // Hide the "Edit" buttons
            binding.reminderDetailsEditButtons.visibility = View.GONE
            // Set up the "Add" button
            binding.buttonAdd.setOnClickListener {
                updateReminder()
            }
        }

        // Set up the date picker
        binding.editTextDate.isEnabled = false
        binding.imageViewCalendar.setOnClickListener {
            showDatePickerDialog(it)
        }
        // Observe the selected date and update the corresponding text view
        viewModel.reminderDateLiveData.observe(viewLifecycleOwner) {
            binding.editTextDate.setText(it)
        }

        // Set up the time picker
        binding.editTextTime.isEnabled = false
        binding.imageViewTime.setOnClickListener {
            showTimePickerDialog(it)
        }
        // Observe the selected time and update the corresponding text view
        viewModel.reminderTimeLiveData.observe(viewLifecycleOwner) {
            binding.editTextTime.setText(it)
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
            reminder.time
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

    private fun showDatePickerDialog(v: View) {
        val newFragment = DatePickerFragment(binding.editTextDate.text.toString())
        newFragment.show(parentFragmentManager, "datePicker")
    }

    private fun showTimePickerDialog(v: View) {
        val newFragment = TimePickerFragment(binding.editTextTime.text.toString())
        newFragment.show(parentFragmentManager, "timePicker")
    }

}

class DatePickerFragment(date: String) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val viewModel by activityViewModels<RemindersViewModel>()

    private val c = Calendar.getInstance()
    private var year = c.get(Calendar.YEAR)
    private var month = c.get(Calendar.MONTH)
    private var day = c.get(Calendar.DAY_OF_MONTH)

    init {
        if (date != "") {
            val dateParts = date.split("/")
            day = dateParts[0].toInt()
            month = dateParts[1].toInt() - 1
            year = dateParts[2].toInt()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        viewModel.setReminderDate("$day/${month + 1}/$year")
    }
}

class TimePickerFragment(time: String) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private val viewModel by activityViewModels<RemindersViewModel>()

    private val c = Calendar.getInstance()
    private var hour = c.get(Calendar.HOUR_OF_DAY)
    private var minute = c.get(Calendar.MINUTE)

    init {
        if (time != "") {
            val timeArray = time.split(":")
            hour = timeArray[0].toInt() - 1
            minute = timeArray[1].toInt()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(
            requireContext(),
            this,
            hour,
            minute,
            is24HourFormat(requireContext())
        )
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        viewModel.setReminderTime("$hourOfDay:${if (minute < 10) "0$minute" else minute}")
    }

}
