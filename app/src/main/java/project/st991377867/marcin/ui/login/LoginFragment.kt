package project.st991377867.marcin.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import project.st991377867.marcin.MainActivity
import project.st991377867.marcin.R
import project.st991377867.marcin.data.model.User
import project.st991377867.marcin.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    companion object {
        const val TAG = "LoginFragment"
    }

    private val firestore = FirebaseFirestore.getInstance()
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login, container, false)
        binding.authButton.setOnClickListener { launchSignInFlow() }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (FirebaseAuth.getInstance().currentUser != null) {
            redirectToFragment(R.id.nav_home)
        } else {
            activity?.findViewById<View>(R.id.btm_nav_view)?.visibility = View.GONE
            (activity as MainActivity).toggleDrawer(false)
            (activity as MainActivity).toggleBottomNav(false)
        }
    }

    @SuppressLint("ResourceType")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LoginViewModel.SIGN_IN_RESULT_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in user.
                Log.i(
                    TAG,
                    "Successfully signed in user " +
                            "${FirebaseAuth.getInstance().currentUser?.displayName}!"
                )
                // Add user to database if response is new
                if (response?.isNewUser == true) {
                    val user = User(FirebaseAuth.getInstance().uid!!)
                    viewModel.addUserData(user)
                // If user it not new, but not in database then add user
                } else if (firestore.collection("users").document(FirebaseAuth.getInstance().uid!!).get().addOnSuccessListener { document ->
                        if (document != null && !document.exists()) {
                            val user = User(FirebaseAuth.getInstance().uid!!)
                            viewModel.addUserData(user)
                        }
                    }.isSuccessful) {
                }
                // Redirect to home screen
                redirectToFragment(R.id.nav_home)
            } else {
                // Sign in failed. If response is null the user canceled the sign-in flow using
                // the back button. Otherwise check response.getError().getErrorCode() and handle
                // the error.
                Log.i(TAG, "Sign in unsuccessful ${response?.error?.errorCode}")
            }
        }
    }

    private fun launchSignInFlow() {
        // Give users the option to sign in / register with their email or Google account.
        // If users choose to register with their email,
        // they will need to create a password as well.
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
        )

        // Create and launch the sign-in intent.
        // We listen to the response of this activity with the
        // SIGN_IN_REQUEST_CODE.
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            LoginViewModel.SIGN_IN_RESULT_CODE
        )
    }

    private fun redirectToFragment(fragmentId: Int) {
        activity?.findViewById<View>(R.id.btm_nav_view)?.visibility = View.VISIBLE
        (activity as MainActivity).toggleDrawer(true)
        (activity as MainActivity).toggleBottomNav(true)
        NavHostFragment.findNavController(this).navigate(R.id.nav_home)
    }

}