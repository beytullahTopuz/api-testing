package com.t4zb.kotlinapitesting.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.databinding.ActivityMainBinding
import com.t4zb.kotlinapitesting.model.Result
import com.t4zb.kotlinapitesting.model.Movies
import com.t4zb.kotlinapitesting.services.MovieAPI
import com.t4zb.kotlinapitesting.ui.fragment.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var movieList: ArrayList<Result>? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //    supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val homeFragment = HomeFragment()
        fragmentTransaction.replace(R.id.frame_layout, homeFragment).commit()

    }

    fun loadAPI() {
        //   : https://api.themoviedb.org
        val BASE_URL = "https://api.themoviedb.org"


        var retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        var service = retrofit.create(MovieAPI::class.java)
        var call = service.getAllData()

        call.enqueue(object : Callback<Movies> {
            override fun onResponse(
                call: Call<Movies>,
                response: Response<Movies>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {

                        var result = response.body()!!.results


                        var str: String = ""

                        for (i in result) {
                            println(i.original_title)
                            movieList?.add(i)

                            str += "\n${i.original_title}"
                        }
                        //       binding.textView2.text = str;
                    }
                }
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                println("Error ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}