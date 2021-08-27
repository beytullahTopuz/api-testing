package com.t4zb.kotlinapitesting.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.databinding.FragmentHomeBinding
import com.t4zb.kotlinapitesting.modelLayer.rest.service.response.MoviesPopularity
import com.t4zb.kotlinapitesting.ui.adapter.MoviePopAdapter
import com.t4zb.kotlinapitesting.ui.fragment.basefragment.BaseFragment
import com.t4zb.kotlinapitesting.ui.viewmodel.SharedViewModel
import java.util.*

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
        setupViewModel()
        initializeViews()
    }

    fun setupViewModel() {
        mSharedViewModel =
            ViewModelProvider(requireActivity(), defaultViewModelProviderFactory).get(
                SharedViewModel::class.java
            )
    }

    fun initializeViews() {
        mSharedViewModel.dataRepo.moviePopularityData.observe(viewLifecycleOwner, { movies ->
            if (movies != null && movies.isNotEmpty()) {
                moviesPopAdapter = MoviePopAdapter(
                    mContext,
                    //Error :   NullPointerException: null cannot be cast to non-null type java.util.ArrayList
                    movies as ArrayList<MoviesPopularity>,
                    mSharedViewModel
                )
                mBinding.topRatedRecyclerView.adapter = moviesPopAdapter
            }
        })
    }

    companion object {

    }
}