package project.st991377867.marcin.ui.goals

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.Delay
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import project.st991377867.marcin.R
import project.st991377867.marcin.data.model.Goal
import project.st991377867.marcin.databinding.FragmentGoalsBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar

class GoalsFragment : Fragment() {

    companion object {
        fun newInstance() = GoalsFragment()
    }

    private lateinit var viewModel: GoalsViewModel
    private lateinit var binding: FragmentGoalsBinding
    private var goal: Goal? = null

    val dateFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_goals, container, false)
        binding = DataBindingUtil.inflate<FragmentGoalsBinding>(inflater, R.layout.fragment_goals, container, false)

        viewModel = GoalsViewModel()

        MainScope().launch {
            viewModel.run()
            viewModel.currentGoal.observe(viewLifecycleOwner){
                goalObserved(it)
            }
            populate()
        }


        // save/update goal
        binding.goalsEditSaveButton.setOnClickListener {
            val goalStatement: String = binding.goalsEditGoalStatement.text.toString()
            try {
                val calorie: String = binding.goalsEditCalorieTargetET.text.toString()

                MainScope().launch {

                    viewModel.setNewGoal(goalStatement, calorie.toInt())
                    viewModel.run()
                    populate()
                    binding.goalsEditGoalStatement.setText("")
                    binding.goalsEditCalorieTargetET.setText("")
                }
            } catch (e: Exception){
                Toast.makeText(activity,"Error Saving Goal", Toast.LENGTH_LONG).show()
            }
        }

        binding.goalsSetGoalButton.setOnClickListener {
            binding.goalsEditGoalGroup.visibility = View.VISIBLE
            binding.goalsDisplayGoalGroup.visibility = View.GONE
            binding.goalsNoGoalGroup.visibility = View.GONE
            binding.goalsEditGoalStatement.setText("")
            binding.goalsEditCalorieTargetET.setText("")
        }

        binding.goalsDeleteButton.setOnClickListener {
            MainScope().launch {
                viewModel.deleteGoal()
                goal = null
                populate()
            }
        }

        binding.goalsEditButton.setOnClickListener {
            binding.goalsEditGoalGroup.visibility = View.VISIBLE
            binding.goalsDisplayGoalGroup.visibility = View.GONE
            binding.goalsNoGoalGroup.visibility = View.GONE
            binding.goalsEditGoalStatement.setText(binding.goalsStatement.text)
            binding.goalsEditCalorieTargetET.setText(binding.goalsDailyCalorieGoal.text)
        }
        return binding.root
    }

    suspend fun populate(){
        MainScope().launch {
            val dailyCalorieCount: Int = viewModel.getDailyCalorie()
            if (goal == null){
                binding.goalsNoGoalGroup.visibility = View.VISIBLE
                binding.goalsDisplayGoalGroup.visibility = View.GONE
                binding.goalsEditGoalGroup.visibility = View.GONE
            } else {
                binding.goalsDailyCalorieGoal.text = goal!!.calories
                binding.goalsDailyCalorieActual.text = dailyCalorieCount.toString()// change
                binding.goalsSetDate.text = goal!!.date
                binding.goalsStatement.text = goal!!.goal

                binding.goalsDisplayGoalGroup.visibility = View.VISIBLE
                binding.goalsNoGoalGroup.visibility = View.GONE
                binding.goalsEditGoalGroup.visibility = View.GONE
            }
        }
    }

    fun goalObserved(g: Goal){
        MainScope().launch {
            goal = g
            populate()
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GoalsViewModel::class.java)
    }

}