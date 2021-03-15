package com.mburakcakir.taketicket.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.bumptech.glide.Glide
import com.mburakcakir.taketicket.R
import com.mburakcakir.taketicket.databinding.ActivityMainBinding
import com.mburakcakir.taketicket.util.SessionManager
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var sessionManager: SessionManager
    private lateinit var profileFragmentItem: MenuItem
    private lateinit var profileImage: CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
//        val host: NavHostFragment = supportFragmentManager
//            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
//        binding.toolbar.setupWithNavController(host.navController, appBarConfiguration)
        Log.v("logImage", "onCreate")
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        Log.v("logImage", "onCreateOptionsMenu")
        profileFragmentItem = binding.toolbar.menu.findItem(R.id.profileFragment)
        profileImage = profileFragmentItem.actionView.findViewById(R.id.imgProfilePicture)
        sessionManager.getImageUri()?.let {
            Glide.with(this).load(it).into(profileImage)
        }

        profileFragmentItem.actionView.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_profileFragment)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.v("logImage", "onOptionsItemSelected")
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.v("logImage", "onSupportNavigateUp")
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun changeToolbarVisibility(visibility: Int) {
        binding.toolbar.visibility = visibility
    }
}

