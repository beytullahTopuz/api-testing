package com.t4zb.kotlinapitesting.ui.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.google.android.material.transition.MaterialFadeThrough
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.appUser.AppUser
import com.t4zb.kotlinapitesting.databinding.ActivityMainBinding
import com.t4zb.kotlinapitesting.ui.fragment.HomeFragmentDirections
import com.t4zb.kotlinapitesting.util.showLogDebug
import com.t4zb.kotlinapitesting.util.showToast
import nl.joery.animatedbottombar.AnimatedBottomBar


class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {
    private lateinit var mBinding: ActivityMainBinding

    private val currentNavFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            ?.childFragmentManager
            ?.fragments
            ?.first()

    private val runtimePermissions = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.ACCESS_NETWORK_STATE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        supportActionBar?.hide()
        requestPermissions()
        navigateToHome()

        mBinding.bottomNavigationView.setOnTabSelectListener(object :
            AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                when (newIndex) {
                    0 -> {
                        findNavController(R.id.nav_host_fragment).navigateUp()
                    }
                    1 -> {

                        var directions =
                            HomeFragmentDirections.actionHomeFragmentDirectionToFavoriteFragment()
                        findNavController(R.id.nav_host_fragment).navigate(directions)
                    }
                    2 -> {

                        if (AppUser.getFirebaseUser() == null) {

                            //login fragment
                            val directions =
                                HomeFragmentDirections.actionHomeFragmentDirectionToRegisterFragment()
                            findNavController(R.id.nav_host_fragment).navigate(directions)


                            // register fragment
                            /* val directions =
                                  HomeFragmentDirections.actionHomeFragmentDirectionToRegisterFragment()
                              findNavController(R.id.nav_host_fragment).navigate(directions)
                           */
                        } else {//profile fragment

                            val directions =
                                HomeFragmentDirections.actionHomeFragmentDirectionToProfileFragment()
                            findNavController(R.id.nav_host_fragment).navigate(directions)
                        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun String.permissionGranted(ctx: Context) =
        ContextCompat.checkSelfPermission(ctx, this) == PackageManager.PERMISSION_GRANTED

    private fun hasPermissions(): Boolean {
        return runtimePermissions.count { !it.permissionGranted(this) } == 0
    }

    fun requestPermissions() {
        if (!hasPermissions()) {
            ActivityCompat.requestPermissions(
                this,
                runtimePermissions, requestCodeAskPerms
            )
        } else {
            showLogDebug("Permissions Granted: ", "All")

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            requestCodeAskPerms -> {
                for (index in runtimePermissions.indices) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        var notGrantedMessage =
                            "Required permission ${permissions[index]} not granted."

                        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                permissions[index]
                            )
                        ) {
                            notGrantedMessage += getString(R.string.error_no_perm)
                        }

                        showToast(this, notGrantedMessage)
                    }
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val requestCodeAskPerms = 1
    }
}