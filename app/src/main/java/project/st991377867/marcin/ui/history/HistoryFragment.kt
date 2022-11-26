package project.st991377867.marcin.ui.history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import project.st991377867.marcin.R
import project.st991377867.marcin.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private lateinit var viewModel: HistoryViewModel
    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_history, container, false)
        binding = DataBindingUtil.inflate<FragmentHistoryBinding>(inflater, R.layout.fragment_history, container, false)



        binding.sevenDays.setOnClickListener {
            binding.historyList.text = viewModel.RecentHistory(7)
        }

        binding.thirtyDays.setOnClickListener {
            binding.historyList.text = viewModel.RecentHistory(30)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        // TODO: Use the ViewModel



    }

}