package com.t4zb.kotlinapitesting

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast

import com.t4zb.kotlinapitesting.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {

    private lateinit var bindigSplash: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindigSplash = ActivitySplashBinding.inflate(layoutInflater)
        val view = bindigSplash.root
        setContentView(view)


        supportActionBar?.hide()

        if (!isNetworkConnected()){

            val toast = Toast.makeText(applicationContext,"You're offline. Check your connection",Toast.LENGTH_LONG)
            toast.show()
        }
            Handler().postDelayed({
                val intent =Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            },4000)




    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }



}