package com.t4zb.kotlinapitesting.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.databinding.FragmentHomeBinding
import com.t4zb.kotlinapitesting.helper.PicassoHelper
import com.t4zb.kotlinapitesting.modelLayer.rest.service.response.MoviesPopularity
import com.t4zb.kotlinapitesting.modelLayer.rest.service.response.MoviesTopRated
import com.t4zb.kotlinapitesting.ui.adapter.MoviePopAdapter
import com.t4zb.kotlinapitesting.ui.adapter.MovieTopRatedAdapter
import com.t4zb.kotlinapitesting.ui.contract.BaseContract
import com.t4zb.kotlinapitesting.ui.fragment.basefragment.BaseFragment
import com.t4zb.kotlinapitesting.ui.presenter.BasePresenter
import com.t4zb.kotlinapitesting.ui.viewmodel.SharedViewModel
import com.t4zb.kotlinapitesting.util.Constants
import com.t4zb.kotlinapitesting.util.ViewUtils
import com.t4zb.kotlinapitesting.util.expandView
import com.t4zb.kotlinapitesting.util.showLogDebug
import nl.joery.animatedbottombar.AnimatedBottomBar
import java.util.*
import kotlin.math.abs

class HomeFragment : BaseFragment(R.layout.fragment_home), BaseContract.ViewMain,
    AppBarLayout.OnOffsetChangedListener {
    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mContext: FragmentActivity

    private lateinit var mSharedViewModel: SharedViewModel

    private lateinit var moviesPopAdapter: MoviePopAdapter

    private lateinit var moviesTopRatedAdapter: MovieTopRatedAdapter

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

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }


    }

    override fun setupViewModel() {
        mSharedViewModel =
            ViewModelProvider(requireActivity(), defaultViewModelProviderFactory).get(
                SharedViewModel::class.java
            )
    }

    override fun initializeViews() {
        mBinding.appBarLayout.post {
            val height = ViewUtils.getScreenWidth(mContext) * 1 / 3
            setOffset(height)
        }

        mBinding.toolbarBanner.singleCardView.layoutParams.height =
            ViewUtils.getScreenWidth(mContext)

        mBinding.appBarLayout.addOnOffsetChangedListener(this)

        mBinding.popularRecyclerView.layoutManager = GridLayoutManager(mContext, 2)
        mBinding.topRatedRecyclerView.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

        mSharedViewModel.dataRepo.movieTopRatedData.observe(viewLifecycleOwner, {
            if (it != null && it.isNotEmpty()) {
                moviesTopRatedAdapter = MovieTopRatedAdapter(
                    mContext,
                    it as ArrayList<MoviesTopRated>,
                    mSharedViewModel
                )
                mBinding.topRatedRecyclerView.adapter = moviesTopRatedAdapter
            }
        })

        mSharedViewModel.dataRepo.movieBanner.observe(viewLifecycleOwner, {movies ->
            mBinding.let {
                it.toolbarBanner.singleMovieBannerMoreInfo.text = movies.overview
                PicassoHelper.picassoOkhttp(
                    mContext,
                    movies.poster_path,
                    mBinding.toolbarBanner.singleMovieBannerImage
                )
            }
            mBinding.toolbarBanner.singleMovieBannerMore.setOnClickListener {
                exitTransition = MaterialElevationScale(false).apply {
                    duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
                }
                reenterTransition = MaterialElevationScale(true).apply {
                    duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
                }
                val transitionName = mContext.getString(R.string.detail_card_transition)
                val extras = FragmentNavigatorExtras(mBinding.toolbarBanner.singleCardView to transitionName)
                val directions = HomeFragmentDirections.actionHomeFragmentDirectionToDetailFragment2()
                mSharedViewModel.selectedMoviePop.value = movies
                mSharedViewModel.movieType.value = Constants.MOVIE_TYPE_POPULAR
                Navigation.findNavController(mBinding.toolbarBanner.singleCardView).navigate(directions, extras)
                showLogDebug(TAG, mSharedViewModel.selectedMoviePop.value.toString())
            }
        })

        mSharedViewModel.dataRepo.moviePopularityData.observe(viewLifecycleOwner, { movies ->
            if (movies != null && movies.isNotEmpty()) {
                moviesPopAdapter = MoviePopAdapter(
                    mContext,

                    movies as ArrayList<MoviesPopularity>,
                    mSharedViewModel
                )
                mBinding.popularRecyclerView.adapter = moviesPopAdapter
            }
        })

        val bar = mContext.findViewById<AnimatedBottomBar>(R.id.bottomNavigationView)
        bar?.visibility = View.VISIBLE
    }

    fun setOffset(offsetPx: Int) {
        val params = mBinding.appBarLayout.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as AppBarLayout.Behavior
        behavior.onNestedPreScroll(
            mBinding.root,
            mBinding.appBarLayout,
            mBinding.fragNestedLyt,
            0,
            offsetPx,
            intArrayOf(0, 0)
        )
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        val maxScroll = mBinding.appBarLayout.totalScrollRange
        val perc = abs(verticalOffset).toFloat() / maxScroll.toFloat()
        showLogDebug(TAG, "" + perc)
        if (perc >= .9f) {
            mBinding.toolbarHeaderView.header.visibility = View.VISIBLE
            mBinding.toolbarBanner.root.visibility = View.INVISIBLE
            mBinding.toolbar.background =
                ContextCompat.getDrawable(mContext, R.color.white)
        } else if (perc < .9f) {
            mBinding.toolbarHeaderView.header.visibility = View.GONE
            mBinding.toolbarBanner.root.visibility = View.VISIBLE
            mBinding.toolbar.background = ContextCompat.getDrawable(mContext, R.color.transparent)
        }
    }

    companion object {
        private const val TAG = "HomeFragment"
    }
}