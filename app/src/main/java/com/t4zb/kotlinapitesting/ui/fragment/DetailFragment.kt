package com.t4zb.kotlinapitesting.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.transition.MaterialContainerTransform
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.databinding.FragmentDetailBinding
import com.t4zb.kotlinapitesting.helper.PicassoHelper
import com.t4zb.kotlinapitesting.ui.contract.BaseContract
import com.t4zb.kotlinapitesting.ui.fragment.basefragment.BaseFragment
import com.t4zb.kotlinapitesting.ui.presenter.BasePresenter
import com.t4zb.kotlinapitesting.ui.viewmodel.SharedViewModel
import com.t4zb.kotlinapitesting.util.Constants


class DetailFragment : BaseFragment(R.layout.fragment_detail), BaseContract.ViewMain {

    private lateinit var mContext: FragmentActivity

    private lateinit var mSharedViewModel: SharedViewModel

    private lateinit var mainBinding: FragmentDetailBinding

    private val mPresenter: BasePresenter by lazy {
        BasePresenter(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainBinding = FragmentDetailBinding.bind(view)
        mPresenter.onViewsCreated()

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(Color.CYAN)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()

    }

    companion object {
        private const val TAG = "DetailFragment"
    }

    override fun setupViewModel() {
        mSharedViewModel =
            ViewModelProvider(requireActivity(), defaultViewModelProviderFactory).get(
                SharedViewModel::class.java
            )


    }

    override fun initializeViews() {

        when (mSharedViewModel.movieType.value) {
            Constants.MOVIE_TYPE_POPULAR -> {
                mSharedViewModel.selectedMoviePop.observe(viewLifecycleOwner, Observer { movies ->
                    mainBinding.run {
                        mSharedViewModel.selectedMoviePop.observe(viewLifecycleOwner, Observer {
                            if (it != null) {
                                mainBinding.textViewTitle.text = it.title
                                mainBinding.textViewOverview.text = it.overview
                                mainBinding.dRelease.text = it.release_date
                                mainBinding.dLanguage.text = it.original_language
                                mainBinding.dRating.rating = (it.vote_average/2).toFloat()
                                mainBinding.dAverage.text = (it.popularity).toString()

                                PicassoHelper.picassoOkhttp(
                                    mContext,
                                    it.poster_path,
                                    mainBinding.imageViewPoster
                                )

                                PicassoHelper.picassoOkhttp(
                                    mContext,
                                    it.backdrop_path,
                                    mainBinding.imageViewBackdrop
                                )
                            }
                        })
                    }
                })
            }
            Constants.MOVIE_TYPE_TOP_RATED -> {
                mSharedViewModel.selectedMovieTopRated.observe(viewLifecycleOwner, Observer { movies ->
                    mainBinding.run {
                        if (movies != null) {
                            mainBinding.textViewTitle.text = movies.title
                            mainBinding.textViewOverview.text = movies.overview
                            mainBinding.dRelease.text = movies.release_date
                            mainBinding.dLanguage.text = movies.original_language
                            mainBinding.dRating.rating = (movies.vote_average/2).toFloat()
                            mainBinding.dAverage.text = (movies.popularity).toString()

                            PicassoHelper.picassoOkhttp(
                                mContext,
                                movies.poster_path,
                                mainBinding.imageViewPoster
                            )

                            PicassoHelper.picassoOkhttp(
                                mContext,
                                movies.backdrop_path,
                                mainBinding.imageViewBackdrop
                            )
                        }
                    }
                })
            }
        }
    }
}