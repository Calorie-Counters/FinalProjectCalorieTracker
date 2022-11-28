package project.st991377867.marcin.ui.diets

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.st991377867.marcin.R
import project.st991377867.marcin.data.model.Diet
import project.st991377867.marcin.data.model.Notification
import project.st991377867.marcin.databinding.FragmentDietsBinding
import project.st991377867.marcin.ui.notifications.NotificationRecyclerView

class DietsFragment : Fragment() {

    companion object {
        fun newInstance() = DietsFragment()
    }

    private val viewModel: DietsViewModel by lazy {
        ViewModelProvider(this).get(DietsViewModel::class.java)
    }
    private lateinit var binding: FragmentDietsBinding
    private lateinit var recordRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDietsBinding.inflate(inflater)
        recordRecyclerView = binding.recyclerView

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list: List<Diet> = viewModel.getDummyDiets()

        recordRecyclerView.adapter = DietRecyclerView(list)
        recordRecyclerView.layoutManager = LinearLayoutManager(activity)
        recordRecyclerView.setHasFixedSize(true)
    }

}