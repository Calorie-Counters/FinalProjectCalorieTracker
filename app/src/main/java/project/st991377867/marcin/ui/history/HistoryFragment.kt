package project.st991377867.marcin.ui.history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.st991377867.marcin.R
import project.st991377867.marcin.data.model.DailyCalorieIntake
import project.st991377867.marcin.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private lateinit var viewModel: HistoryViewModel
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var recordRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_history, container, false)
        binding = DataBindingUtil.inflate<FragmentHistoryBinding>(inflater, R.layout.fragment_history, container, false)


        recordRecyclerView = binding.historyView

        /*var recordList: List<DailyCalorieIntake> = emptyList()
        recordRecyclerView.adapter = HistoryRecyclerView(recordList)
        recordRecyclerView.layoutManager = LinearLayoutManager(activity)
        recordRecyclerView.setHasFixedSize(false)*/

        binding.sevenDays.setOnClickListener {
            //binding.historyList.text = viewModel.recentHistory(7)
            val list: List<DailyCalorieIntake> = viewModel.recentHistory(7).values.toList()

            recordRecyclerView

            recordRecyclerView.adapter = HistoryRecyclerView(list)
            recordRecyclerView.layoutManager = LinearLayoutManager(activity)
            recordRecyclerView.setHasFixedSize(true)

            /*recordList = viewModel.recentHistory(7).values.toList()
            recordRecyclerView?.adapter?.notifyDataSetChanged()*/
        }

        binding.thirtyDays.setOnClickListener {
            //binding.historyList.text = viewModel.recentHistory(30)
            val list: List<DailyCalorieIntake> = viewModel.recentHistory(30).values.toList()
            recordRecyclerView.adapter = HistoryRecyclerView(list)
            recordRecyclerView.layoutManager = LinearLayoutManager(activity)
            recordRecyclerView.setHasFixedSize(true)
            /*recordList = viewModel.recentHistory(30).values.toList()
            recordRecyclerView?.adapter?.notifyDataSetChanged()*/
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)



    }

}