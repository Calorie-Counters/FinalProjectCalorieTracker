package project.st991377867.marcin.ui.notifications

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.st991377867.marcin.R
import project.st991377867.marcin.data.model.Notification
import project.st991377867.marcin.data.model.Reminder
import project.st991377867.marcin.databinding.FragmentNotificationsBinding
import project.st991377867.marcin.ui.reminders.RemindersRecyclerView

class NotificationsFragment : Fragment() {

    companion object {
        fun newInstance() = NotificationsFragment()
    }

    private val viewModel: NotificationsViewModel by lazy {
        ViewModelProvider(this).get(NotificationsViewModel::class.java)
    }
    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var recordRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationsBinding.inflate(inflater)
        recordRecyclerView = binding.recyclerView

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list: List<Notification> = viewModel.getDummyNotifications()

        recordRecyclerView.adapter = NotificationRecyclerView(list)
        recordRecyclerView.layoutManager = LinearLayoutManager(activity)
        recordRecyclerView.setHasFixedSize(true)
    }

}