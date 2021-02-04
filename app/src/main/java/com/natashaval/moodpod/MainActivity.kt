package com.natashaval.moodpod

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.natashaval.moodpod.databinding.ActivityMainBinding
import com.natashaval.moodpod.ui.dashboard.DashboardFragmentDirections
import com.natashaval.moodpod.ui.home.HomeFragmentDirections
import com.natashaval.moodpod.utils.ViewUtils.setSafeClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private val navController by lazy {
    findNavController(R.id.nav_host_fragment)
  }
  private lateinit var appBarConfiguration: AppBarConfiguration

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val navView: BottomNavigationView = binding.navView

    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    appBarConfiguration = AppBarConfiguration(
        setOf(R.id.navigation_home, R.id.navigation_profile))
    setupActionBarWithNavController(navController, appBarConfiguration)
    navView.setupWithNavController(navController)

    with(binding) {
      fab.setSafeClickListener {
        when (navController.currentDestination?.id) {
          R.id.navigation_home -> {
            val action = HomeFragmentDirections.actionNavigationHomeToNavigationMood(null)
            navController.navigate(action)
          }
          R.id.navigation_profile -> {
            val action = DashboardFragmentDirections.actionNavigationProfileToNavigationMood2(null)
            navController.navigate(action)
          }
        }
      }
    }

    findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { _, destination, _ ->
      when(destination.id) {
        R.id.navigation_home, R.id.navigation_profile -> showBottomNav(true)
        else -> showBottomNav(false)
      }
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    // https://www.raywenderlich.com/8279305-navigation-component-for-android-part-3-transition-and-navigation
    return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
  }

  private fun showBottomNav(isShow: Boolean = false) {
    binding.bottomAppBar.visibility = if (isShow) View.VISIBLE else View.GONE
    if (isShow) binding.fab.show() else binding.fab.hide()
  }
}