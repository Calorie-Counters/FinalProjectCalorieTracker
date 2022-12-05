package project.st991377867.marcin.ui.diets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import project.st991377867.marcin.databinding.FragmentDietDetailsBinding

class DietDetailsFragment : Fragment() {

    val TAG = "DietDetailsFragment"

    private val navigationArgs: DietDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDietDetailsBinding

    private val viewModel by activityViewModels<DietsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDietDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.dietId

        viewModel.requestDiet(id).observe(viewLifecycleOwner) {
            binding.diet = it
        }
    }

}