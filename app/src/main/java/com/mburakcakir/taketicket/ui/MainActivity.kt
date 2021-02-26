package com.mburakcakir.taketicket.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.bumptech.glide.Glide
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.databinding.ActivityMainBinding
import com.mburakcakir.taketicket.utils.SessionManager
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        val appBarConfiguration = AppBarConfiguration.Builder(R.id.eventFragment).build()
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        changeToolbarVisibility(View.GONE)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun changeToolbarVisibility(visibility: Int) {
        binding.toolbar.visibility = visibility
    }

    fun changeToolbarTitle(title: String) {
        binding.toolbar.title = title
    }

    fun initToolbarProfile() {
        val profileFragmentItem = binding.toolbar.menu.findItem(R.id.profileFragment)
        val profileImage =
            profileFragmentItem.actionView.findViewById<CircleImageView>(R.id.imgProfilePicture)
        val imageUri = Uri.parse(sessionManager.getImageUri())
        Log.v("mainImageUri", sessionManager.getImageUri()!!)
        Glide.with(this).load(imageUri).into(profileImage)

        profileFragmentItem.actionView.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_profileFragment)
        }
    }
}

