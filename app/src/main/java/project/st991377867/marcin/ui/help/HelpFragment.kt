package project.st991377867.marcin.ui.help

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.*
import project.st991377867.marcin.R
import project.st991377867.marcin.data.model.User
import project.st991377867.marcin.databinding.FragmentHelpBinding

class HelpFragment: Fragment() {
    companion object {
        fun newInstance() = HelpFragment()
    }

    private val viewModel: HelpViewModel by lazy {
        ViewModelProvider(this).get(HelpViewModel::class.java)
    }
    private lateinit var binding: FragmentHelpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //viewModel = HelpViewModel()
        binding = DataBindingUtil.inflate<FragmentHelpBinding>(inflater, R.layout.fragment_help, container, false)

        if (User.isAdmin()){
            binding.helpFragmentAdminEditButton.visibility = View.VISIBLE
        } else {
            binding.helpFragmentAdminEditButton.visibility = View.GONE
        }

        MainScope().launch {
            viewModel.run()
        }

        viewModel.helpText.observe(viewLifecycleOwner){
            setHelpText(it)
        }

        binding.helpFragmentAdminEditButton.setOnClickListener {
            val action = HelpFragmentDirections.actionHelpFragmentToNavHelpEdit()
            findNavController().navigate(action)
        }


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    fun setHelpText(text: String){
        binding.helpTxt = text
    }

}