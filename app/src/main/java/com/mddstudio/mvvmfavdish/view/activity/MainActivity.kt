package com.mddstudio.mvvmfavdish.view.activity

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mddstudio.mvvmfavdish.R
import com.mddstudio.mvvmfavdish.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

         navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        val navBar: BottomNavigationView =
            binding.navView
        if (true) {
            navBar.visibility = View.VISIBLE
        }
        binding.navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)


    }
    fun hidebuttonNavi(){
        binding.navView.clearAnimation()
        binding.navView.animate().translationY(binding.navView.height.toFloat()).duration=300
        binding.navView.visibility = View.GONE
    }
    fun showbuttonNavi(){
        binding.navView.clearAnimation()
        binding.navView.animate().translationY(0f).duration=300
        binding.navView.visibility = View.VISIBLE
    }
}