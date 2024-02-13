package com.sbapps.scheduleplus.presentation

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sbapps.scheduleplus.R
import com.sbapps.scheduleplus.databinding.ActivityMainBinding
import com.sbapps.scheduleplus.presentation.onboarding.OnBoardingFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val PREFS = "prefs"
        private const val NIGHT_MODE_KEY = "night_mode_key"
        private const val SHOW_BOARDING = "show_boarding"
        private const val URL_GITHUB = "https://github.com/YuryShchasny"
        private const val URL_TELEGRAM = "https://t.me/+5KOe_nWbcXwwYzcy"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE)
        val isDarkTheme = sharedPreferences.getBoolean(NIGHT_MODE_KEY, getDefaultNightMode())
        binding.switchNightMode.isChecked = isDarkTheme
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        setDrawerLayout()
        setNavController()
        setListeners()
        showOnBoarding(isDarkTheme)
    }

    private fun showOnBoarding(isDarkTheme: Boolean) {
        sharedPreferences = getSharedPreferences(PREFS, MODE_PRIVATE)
        when (sharedPreferences.getBoolean(SHOW_BOARDING, true)) {
            true -> {
                val bundle = OnBoardingFragment.getBundle(
                    sharedPreferences.getBoolean(
                        NIGHT_MODE_KEY, isDarkTheme
                    )
                )
                navController.navigate(R.id.onBoardingFragment, bundle)
                sharedPreferences.edit().putBoolean(SHOW_BOARDING, false).apply()
            }

            false -> {
            }
        }
    }

    private fun getDefaultNightMode(): Boolean {
        return when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_NO -> false
            AppCompatDelegate.MODE_NIGHT_YES -> true
            else -> {
                when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                    Configuration.UI_MODE_NIGHT_NO -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        return false
                    }

                    Configuration.UI_MODE_NIGHT_YES -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        return true
                    }

                    else -> {
                        return false
                    }
                }
            }
        }
    }

    private fun setListeners() {
        binding.switchNightMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                sharedPreferences.edit().putBoolean(NIGHT_MODE_KEY, true).apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                sharedPreferences.edit().putBoolean(NIGHT_MODE_KEY, false).apply()
            }
        }
        binding.textViewTelegram.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(URL_TELEGRAM)))
        }
        binding.textViewDeveloper.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(URL_GITHUB)))
        }

    }

    private fun setDrawerLayout() {
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun setNavController() {
        navController = findNavController(R.id.nav_host_fragment_activity_main)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (getNavViewVisibility(destination)) {
                binding.navView.visibility = View.GONE
            } else {
                binding.navView.visibility = View.VISIBLE
            }
        }
        binding.navView.setupWithNavController(navController)
    }

    private fun getNavViewVisibility(destination: NavDestination): Boolean {
        return (destination.id == R.id.navigation_week_edit || destination.id == R.id.onBoardingFragment)
    }
}