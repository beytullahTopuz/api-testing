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
              movies as ArrayList<MoviesPopularity>,
              mSharedViewModel)
        })
    }

    override fun onStart() {
        super.onStart()
        setupViewModel()
    }

    companion object {

    }
}