package project.st991377867.marcin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import project.st991377867.marcin.data.model.User
import project.st991377867.marcin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var user: LiveData<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val btmNavView: BottomNavigationView = findViewById(R.id.btm_nav_view)
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_login, R.id.nav_notifications,
                R.id.nav_reminders, R.id.nav_diets, R.id.nav_history,
                R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        btmNavView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                FirebaseAuth.getInstance().signOut()
                findNavController(R.id.nav_host_fragment_content_main).navigate(R.id.nav_login)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(binding.navView)) {
            binding.drawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        if (FirebaseAuth.getInstance().currentUser != null) {
            user = User.requestLiveUserData()
            setDrawerFields(user)
        } else {
            NavHostFragment.findNavController(
                supportFragmentManager.findFragmentById(
                    R.id.nav_host_fragment_content_main)!!).navigate(R.id.nav_login)
        }
    }

    fun toggleDrawer(enable: Boolean) {
        if (enable) {
            binding.appBarMain.toolbar.visibility = View.VISIBLE
        } else {
            binding.appBarMain.toolbar.visibility = View.GONE
        }
    }

    fun toggleBottomNav(enable: Boolean) {
        if (enable) {
            findViewById<BottomNavigationView>(R.id.btm_nav_view).visibility = View.VISIBLE
        } else {
            findViewById<BottomNavigationView>(R.id.btm_nav_view).visibility = View.GONE
        }
    }

    private fun setDrawerFields(userLiveData: LiveData<User>) {
        val user = userLiveData.value
        val headerView = binding.navView.getHeaderView(0)
        val fullName = headerView.findViewById<TextView>(R.id.textViewFullName)
        val email = headerView.findViewById<TextView>(R.id.textViewEmail)
        if (user != null) {
            userLiveData.observe(this) {
                fullName.text =
                    if (user.firstName != "")
                        "${user.firstName} ${user.lastName}"
                    else
                        user.displayName
                email.text = user.email
            }
        }
    }

}