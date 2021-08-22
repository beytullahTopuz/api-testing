package com.t4zb.kotlinapitesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.t4zb.kotlinapitesting.databinding.ActivityMainBinding
import com.t4zb.kotlinapitesting.model.Movies
import com.t4zb.kotlinapitesting.model.Result
import com.t4zb.kotlinapitesting.services.MovieAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var movieList : ArrayList<Result>? = null ;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    //    supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view  = binding.root
        setContentView(view)


        binding.button.setOnClickListener {
            println("API getting")
           loadAPI()
        }
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
                if (response.isSuccessful){
                    if (response.body() != null){


                        var result = response.body()!!.results


                        var str: String = ""

                        for (i in result){
                            println(i.original_title)
                            movieList?.add(i)

                            str+= "\n${i.original_title}"
                        }
                        binding.textView2.text = str;





                    }
                }

            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {

                println("Error ${t.message}")
            }

        })
    }
}