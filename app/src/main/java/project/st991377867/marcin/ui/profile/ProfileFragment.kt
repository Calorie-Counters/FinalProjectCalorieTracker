package project.st991377867.marcin.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
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

    /**
     * Suppress lint warning for showing a Toast message
     */
    @SuppressLint("ShowToast")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // inflate the view and initialize the binding object
        binding = FragmentProfileBinding.inflate(inflater)

        // disable editing for inputDisplayName and inputEmail
        binding.inputDisplayName.isEnabled = false
        binding.inputEmail.isEnabled = false

        // bind user data to the view using the viewModel
        viewModel.getLiveUserData().observe(viewLifecycleOwner) {
            user = it
            binding.user = user
        }

        // bind sync status to the view
        viewModel.getSyncStatus().observe(viewLifecycleOwner) {
            if (it) {
                // hide progress bar and show save button when synced
                binding.progressBackground.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.saveButton.visibility = View.VISIBLE
            } else {
                // show progress bar and hide save button when not synced
                binding.progressBackground.visibility = View.VISIBLE
                binding.progressBar.visibility = View.VISIBLE
                binding.saveButton.visibility = View.GONE
            }
        }

        // set listener for save button to call updateUserData in viewModel
        binding.saveButton.setOnClickListener {
            viewModel.updateUserData()
            // show toast message when user data is saved
            val toast = Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 180)
            toast.show()
        }

        // return the root view
        return binding.root
    }


}
