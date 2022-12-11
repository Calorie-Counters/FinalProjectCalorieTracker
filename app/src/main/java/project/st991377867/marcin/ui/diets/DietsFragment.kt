package project.st991377867.marcin.ui.diets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.st991377867.marcin.databinding.FragmentDietsBinding
import project.st991377867.marcin.adapters.DietListAdapter
import project.st991377867.marcin.data.model.User

class DietsFragment : Fragment() {

    companion object {

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
        binding = FragmentDietsBinding.inflate(inflater, container, false)
        recordRecyclerView = binding.recyclerView

        binding.fab.setImageResource(android.R.drawable.ic_input_add)
        binding.fab.visibility = if (User.isAdmin()) View.VISIBLE else View.GONE
        // prevent from clicking behind the fab
        binding.fab.setOnClickListener {
            val action = DietsFragmentDirections.actionNavDietsToNavDietDetail("New Diet")
            this.findNavController().navigate(action)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DietListAdapter {
            val action = DietsFragmentDirections.actionNavDietsToNavDietDetail("Diet", it.id)
            this.findNavController().navigate(action)
        }
        viewModel.requestDiets()
        viewModel.dietListLiveData.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
    }

}