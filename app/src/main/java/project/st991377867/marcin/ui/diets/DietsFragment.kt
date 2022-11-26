package project.st991377867.marcin.ui.diets

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import project.st991377867.marcin.R

class DietsFragment : Fragment() {

    companion object {
        fun newInstance() = DietsFragment()
    }

    private lateinit var viewModel: DietsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_diets, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DietsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}