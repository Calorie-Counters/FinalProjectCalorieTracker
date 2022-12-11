package project.st991377867.marcin.ui.help

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import project.st991377867.marcin.R
import project.st991377867.marcin.databinding.FragmentHelpEditBinding
import project.st991377867.marcin.databinding.FragmentHistoryBinding

class HelpEditFragment : Fragment(){

    companion object {
        fun newInstance() = HelpEditFragment()
    }

    private val viewModel: HelpViewModel by lazy {
        ViewModelProvider(this).get(HelpViewModel::class.java)
    }
    private lateinit var binding: FragmentHelpEditBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentHelpEditBinding>(inflater, R.layout.fragment_help_edit, container, false)
        //binding.helpTxt = "View items: To view the list fo food items consumed today, navigate to the home page via the bottom navigation bar. To add a food item, press the plus button and fill in the information, then press save.\n\nGoals: A Goal can be set to help keep you consistent with your calorie intake. To view or set a goal, navigate to the Goals section via the navigation drawer which can be opened from the home page using the hamburger button in the top left.\n\nHistory: You can view your calorie intake history via the History section which can be navigated to from the navigation drawer in the homepage. There are options to view history from the past 7 days or 30 days. Summary information such as total calorie intake is displayed for each day, even if no intake records are present for that day. More detailed information can be seen by expanding the cards by pressing on the black box in the card.\n\nDiets: The Diets section provides various options for nutritious and filling meals. You can navigate to the diets page from anywhere within the application via the diets button in the bottom navigation bar.\n\nReminders: Various reminders can be set to help keep you on track for various things like achieving your goal, keeping your mealtimes consistent, or even just reminding you to enter your calorie intake information for the day. You can navigate to the reminders section via the navigation drawer in the homepage."
        MainScope().launch {
            binding.helpTxt = viewModel.getHelpTxt()
        }

        binding.helpEditFragmentCancelButton.setOnClickListener {
            val action = HelpEditFragmentDirections.actionHelpEditFragmentToNavHelp()
            findNavController().navigate(action)
        }

        binding.helpEditFragmentSaveButton.setOnClickListener {
            MainScope().launch {
                val text = binding.helpEditFragmentEditText.text.toString()
                Log.d("HelpEditFrag", text)
                viewModel.saveHelp(text)
            }
        }

        //return inflater.inflate(R.layout.fragment_help_edit, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(HelpViewModel::class.java)
        // TODO: Use the ViewModel
    }
}