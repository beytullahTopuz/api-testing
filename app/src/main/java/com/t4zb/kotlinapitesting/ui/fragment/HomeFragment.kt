package com.t4zb.kotlinapitesting.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.databinding.FragmentHomeBinding
import com.t4zb.kotlinapitesting.modelLayer.rest.service.response.MoviesPopularity
import com.t4zb.kotlinapitesting.ui.adapter.MoviePopAdapter
import com.t4zb.kotlinapitesting.ui.contract.BaseContract
import com.t4zb.kotlinapitesting.ui.fragment.basefragment.BaseFragment
import com.t4zb.kotlinapitesting.ui.presenter.BasePresenter
import com.t4zb.kotlinapitesting.ui.viewmodel.SharedViewModel
import java.util.*

class HomeFragment : BaseFragment(R.layout.fragment_home), BaseContract.ViewMain {
    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mContext: FragmentActivity

    private lateinit var mSharedViewModel: SharedViewModel

    private lateinit var moviesPopAdapter: MoviePopAdapter

    private val mPresenter: BasePresenter by lazy {
        BasePresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mContext = requireActivity()
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentHomeBinding.bind(view)
        mPresenter.onViewsCreated()
    }

    override fun setupViewModel() {
        mSharedViewModel =
            ViewModelProvider(requireActivity(), defaultViewModelProviderFactory).get(
                SharedViewModel::class.java
            )
    }

    override fun initializeViews() {
        mBinding.popularRecyclerView.layoutManager = GridLayoutManager(mContext,2)

        mSharedViewModel.dataRepo.moviePopularityData.observe(viewLifecycleOwner, { movies ->
            if (movies != null && movies.isNotEmpty()) {
                moviesPopAdapter = MoviePopAdapter(
                    mContext,
                    //Error :   NullPointerException: null cannot be cast to non-null type java.util.ArrayList
                    movies as ArrayList<MoviesPopularity>,
                    mSharedViewModel
                )
                mBinding.popularRecyclerView.adapter = moviesPopAdapter
            }
        })
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}