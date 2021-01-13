package com.natashaval.moodpod

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.natashaval.moodpod.databinding.ActivityMainBinding
import com.natashaval.moodpod.ui.home.HomeFragmentDirections
import com.natashaval.moodpod.util.ViewUtils.setSafeClickListener

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var appBarConfiguration: AppBarConfiguration

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val navView: BottomNavigationView = binding.navView

    val navController = findNavController(R.id.nav_host_fragment)
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    appBarConfiguration = AppBarConfiguration(
        setOf(R.id.navigation_home, R.id.navigation_profile))
    setupActionBarWithNavController(navController, appBarConfiguration)
    navView.setupWithNavController(navController)

    with(binding) {
      fab.setSafeClickListener {
        val action = HomeFragmentDirections.actionNavigationHomeToNavigationMood()
        navController.navigate(action)
      }
    }
  }
}