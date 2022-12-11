package project.st991377867.marcin.ui.diets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import project.st991377867.marcin.data.model.Diet
import project.st991377867.marcin.databinding.FragmentDietDetailsBinding
import project.st991377867.marcin.data.model.User

class DietDetailsFragment : Fragment() {

    val TAG = "DietDetailsFragment"

    private val navigationArgs: DietDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDietDetailsBinding

    private val viewModel: DietsViewModel by lazy {
        ViewModelProvider(this).get(DietsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDietDetailsBinding.inflate(inflater)

        binding.editDietDetails.visibility = View.GONE

        binding.fab.setImageResource(android.R.drawable.ic_menu_edit)
        binding.fab.visibility = if (User.isAdmin()) View.VISIBLE else View.GONE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.dietId
        var title = navigationArgs.title

        if (id == "-1") {
            title = "New Diet"
            toggleEditMode()
            binding.fabDelete.visibility = View.GONE

            viewModel.setDiet(Diet())
            viewModel.dietLiveData.observe(viewLifecycleOwner) {
                binding.diet = it
            }

            binding.fab.visibility = View.VISIBLE
            binding.fab.setImageResource(android.R.drawable.ic_input_add)
            binding.fab.setOnClickListener {
                Log.d(TAG, "onViewCreated: ${binding.diet}")
                if (viewModel.isDietValid()) {
                    viewModel.updateDiet(binding.diet!!)
                    activity?.onBackPressed()
                } else {
                    Toast.makeText(context, "Required fields: ${getRequiredFields()}", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            binding.fabDelete.visibility = if (User.isAdmin()) View.VISIBLE else View.GONE

            viewModel.requestDiet(id).observe(viewLifecycleOwner) {
                viewModel.setDiet(it)
                binding.diet = viewModel.dietLiveData.value
            }
            binding.fab.setOnClickListener {
                if (binding.editDietDetails.isVisible) {
                    viewModel.updateDiet(viewModel.dietLiveData.value!!)
                    activity?.onBackPressed()
                } else {
                    toggleEditMode()
                }
            }
        }

        binding.fabDelete.setOnClickListener {
            viewModel.deleteDiet(binding.diet!!)
            activity?.onBackPressed()
        }

    }

    private fun toggleEditMode() {
        if (binding.viewDietDetails.isVisible) {
            binding.viewDietDetails.visibility = View.GONE
            binding.editDietDetails.visibility = View.VISIBLE
            binding.fab.setImageResource(android.R.drawable.ic_menu_save)
        } else {
            binding.viewDietDetails.visibility = View.VISIBLE
            binding.editDietDetails.visibility = View.GONE
            binding.fab.setImageResource(android.R.drawable.ic_menu_edit)
        }
    }

    private fun getRequiredFields(): String {
        val fields = mutableListOf<String>()
        if (binding.editDietDetailsTitleValue.text.isNullOrEmpty()) fields.add("name")
        if (binding.editDietDetailsDescValue.text.isNullOrEmpty()) fields.add("description")
        if (binding.editDietDetailsTypeValue.text.isNullOrEmpty()) fields.add("type")
        if (binding.editDietDetailsDurationValue.text.isNullOrEmpty()) fields.add("duration")
        if (binding.editDietDetailsCaloriesValue.text.isNullOrEmpty()) fields.add("calories")
        if (binding.editDietDetailsIngredientsValue.text.isNullOrEmpty()) fields.add("ingredients")
        if (binding.dietDetailsInstructionsValue.text.isNullOrEmpty()) fields.add("instructions")
        return fields.joinToString(", ")
    }

}