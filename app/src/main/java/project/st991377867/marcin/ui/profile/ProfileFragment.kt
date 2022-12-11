package project.st991377867.marcin.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import project.st991377867.marcin.data.model.User
import project.st991377867.marcin.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    companion object {
        private val TAG = "ProfileFragment"
    }

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<ProfileViewModel>()
    private lateinit var user: User

    @SuppressLint("ShowToast")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)

        // disable editing
        binding.inputDisplayName.isEnabled = false
        binding.inputEmail.isEnabled = false

        // bind user data to view
        viewModel.getLiveUserData().observe(viewLifecycleOwner) {
            user = it
            binding.user = user
        }

        // bind sync status to view
        viewModel.getSyncStatus().observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBackground.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.saveButton.visibility = View.VISIBLE
            } else {
                binding.progressBackground.visibility = View.VISIBLE
                binding.progressBar.visibility = View.VISIBLE
                binding.saveButton.visibility = View.GONE
            }
        }

        // save button listener
        binding.saveButton.setOnClickListener {
            viewModel.updateUserData()
            val toast = Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 180)
            toast.show()
        }

        return binding.root
    }


}