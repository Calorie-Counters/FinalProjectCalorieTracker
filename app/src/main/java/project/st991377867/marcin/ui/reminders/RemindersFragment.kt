package project.st991377867.marcin.ui.reminders

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.st991377867.marcin.R
import project.st991377867.marcin.data.model.Reminder
import project.st991377867.marcin.databinding.FragmentRemindersBinding

class RemindersFragment : Fragment() {

    companion object {
        fun newInstance() = RemindersFragment()
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
        val list: List<Reminder> = viewModel.getDummyReminders()

        recordRecyclerView.adapter = RemindersRecyclerView(list)
        recordRecyclerView.layoutManager = LinearLayoutManager(activity)
        recordRecyclerView.setHasFixedSize(true)
    }

}