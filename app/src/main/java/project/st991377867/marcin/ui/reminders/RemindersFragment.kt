package project.st991377867.marcin.ui.reminders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.st991377867.marcin.R
import project.st991377867.marcin.adapters.ReminderListAdapter
import project.st991377867.marcin.databinding.FragmentRemindersBinding

class RemindersFragment : Fragment() {

    companion object {
        val TAG = "RemindersFragment"
    }

    private val viewModel: RemindersViewModel by viewModels()
    private lateinit var binding: FragmentRemindersBinding
    private lateinit var recordRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentRemindersBinding>(inflater, R.layout.fragment_reminders, container, false)
        recordRecyclerView = binding.recyclerView

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ReminderListAdapter {
            val action = RemindersFragmentDirections.actionNavRemindersToNavReminderDetail("Edit Reminder", it.id)
            this.findNavController().navigate(action)
        }
        viewModel.requestReminders()
        viewModel.reminderListLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.fab.setOnClickListener {
            val action = RemindersFragmentDirections.actionNavRemindersToNavReminderDetail("New Reminder")
            this.findNavController().navigate(action)
        }

    }

}