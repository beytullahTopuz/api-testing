package com.t4zb.kotlinapitesting.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.GridLayout
import android.widget.HorizontalScrollView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.databinding.FragmentHomeBinding
import com.t4zb.kotlinapitesting.modelLayer.rest.service.response.MoviesPopularity
import com.t4zb.kotlinapitesting.ui.activity.MainActivity
import com.t4zb.kotlinapitesting.ui.adapter.MoviePopAdapter
import com.t4zb.kotlinapitesting.ui.fragment.basefragment.BaseFragment
import com.t4zb.kotlinapitesting.ui.viewmodel.SharedViewModel
import java.util.ArrayList

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mContext: FragmentActivity

    private lateinit var mSharedViewModel: SharedViewModel

    private lateinit var moviesPopAdapter: MoviePopAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        mContext = requireActivity()
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentHomeBinding.bind(view)

    }

    fun setupViewModel() {
        mSharedViewModel =
            ViewModelProvider(requireActivity(), defaultViewModelProviderFactory).get(
                SharedViewModel::class.java
            )
    }

    fun initializeViews() {
        mSharedViewModel.dataRepo.moviePopularityData.observe(viewLifecycleOwner, { movies ->
                moviesPopAdapter = MoviePopAdapter(
                    mContext,
                    //Error :   NullPointerException: null cannot be cast to non-null type java.util.ArrayList
                    movies as ArrayList<MoviesPopularity>,
                    mSharedViewModel)

                mBinding.topRatedRecyclerView.adapter = moviesPopAdapter



        }
        )

    }

    override fun onStart() {
        super.onStart()
        setupViewModel()
       // initializeViews()
    }

    companion object {

    }
}