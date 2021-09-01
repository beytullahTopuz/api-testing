package com.t4zb.kotlinapitesting.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.google.android.material.transition.MaterialFadeThrough
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.databinding.ActivityMainBinding
import com.t4zb.kotlinapitesting.ui.fragment.HomeFragmentDirections
import meow.bottomnavigation.MeowBottomNavigation
import nl.joery.animatedbottombar.AnimatedBottomBar

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {
    private lateinit var mBinding: ActivityMainBinding

    private val currentNavFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            ?.childFragmentManager
            ?.fragments
            ?.first()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        supportActionBar?.hide()
        navigateToHome()

        mBinding.bottomNavigationView.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener{
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                when(newIndex) {
                    0 -> {
                        findNavController(R.id.nav_host_fragment).navigateUp()
                    }
                    1->{

                    }
                    2->{
                        val directions = HomeFragmentDirections.actionHomeFragmentDirectionToProfileFragment()
                        findNavController(R.id.nav_host_fragment).navigate(directions)
                    }
                }
            }
        })
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.homeFragment -> {

            }
        }
    }

    fun navigateToHome() {
        mBinding.run {
            findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener(
                this@MainActivity
            )
            currentNavFragment?.apply {
                exitTransition = MaterialFadeThrough().apply {
                    duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}