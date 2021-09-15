package com.t4zb.kotlinapitesting.ui.fragment

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.transition.MaterialContainerTransform
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.appUser.AppUser
import com.t4zb.kotlinapitesting.databinding.FragmentDetailBinding
import com.t4zb.kotlinapitesting.helper.GmsFavoriteHelper
import com.t4zb.kotlinapitesting.helper.PicassoHelper
import com.t4zb.kotlinapitesting.helper.SwipeDismissActions
import com.t4zb.kotlinapitesting.ui.contract.BaseContract
import com.t4zb.kotlinapitesting.ui.fragment.basefragment.BaseFragment
import com.t4zb.kotlinapitesting.ui.presenter.BasePresenter
import com.t4zb.kotlinapitesting.ui.viewmodel.SharedViewModel
import com.t4zb.kotlinapitesting.util.*
import nl.joery.animatedbottombar.AnimatedBottomBar

class DetailFragment : BaseFragment(R.layout.fragment_detail), BaseContract.ViewMain {

    private lateinit var mContext: FragmentActivity

    private lateinit var mSharedViewModel: SharedViewModel

    private lateinit var mainBinding: FragmentDetailBinding

    private lateinit var dialog: Dialog

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

    private fun initDialog(type: Int) {
        dialog = Dialog(mContext, R.style.BlurTheme)
        val window = dialog.window
        window!!.attributes.windowAnimations = type
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        window.setLayout(width, height)
        val wpl = window.attributes
        wpl.gravity = Gravity.BOTTOM
        window.attributes = wpl
        dialog.setContentView(R.layout.share_alert_dialog)
        dialog.setCanceledOnTouchOutside(true)


        val sdShareLink = dialog.findViewById<LinearLayout>(R.id.sd_share_link)

        dialog.show()
    }

    override fun initializeViews() {
        val bar = mContext.findViewById<AnimatedBottomBar>(R.id.bottomNavigationView)
        bar.expandView()
        mainBinding.shareButton.setOnClickListener {
            initDialog(R.style.DialogSlide)
        }
        mainBinding.likeButton.setOnClickListener {
            if (AppUser.getFirebaseUser() == null) {
                showToast(mContext, "please login or register")
            } else {
                if (mSharedViewModel.movieType.value.equals(Constants.MOVIE_TYPE_POPULAR)) {
                    GmsFavoriteHelper.insertFavoritePop(mSharedViewModel.selectedMoviePop.value!!)
                    showSnack(
                        mainBinding.root, resources.getString(
                            R.string.snack_fav,
                            mSharedViewModel.selectedMoviePop.value!!.original_title
                        )
                    )
                    showLogDebug(TAG, "popular")
                }
                if (mSharedViewModel.movieType.value.equals(Constants.MOVIE_TYPE_TOP_RATED)) {
                    GmsFavoriteHelper.insertFavoriteTOP(mSharedViewModel.selectedMovieTopRated.value!!)
                    showSnack(
                        mainBinding.root, resources.getString(
                            R.string.snack_fav,
                            mSharedViewModel.selectedMovieTopRated.value!!.original_title
                        )
                    )
                    showLogDebug(TAG, "top")
                }
            }


        }

        SwipeDismissActions.fragmentDismiss(mainBinding.transitionCard)

        when (mSharedViewModel.movieType.value) {
            Constants.MOVIE_TYPE_POPULAR -> {
                mSharedViewModel.selectedMoviePop.observe(viewLifecycleOwner, Observer { movies ->
                    mainBinding.run {
                        mSharedViewModel.selectedMoviePop.observe(viewLifecycleOwner, {
                            if (it != null) {
                                mainBinding.textViewTitle.text = it.title
                                mainBinding.textViewOverview.text = it.overview
                                mainBinding.dRelease.text = it.release_date
                                mainBinding.dLanguage.text = it.original_language
                                mainBinding.dRating.rating = (it.vote_average / 2).toFloat()
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
                mSharedViewModel.selectedMovieTopRated.observe(viewLifecycleOwner, { movies ->
                    mainBinding.run {
                        if (movies != null) {
                            mainBinding.textViewTitle.text = movies.title
                            mainBinding.textViewOverview.text = movies.overview
                            mainBinding.dRelease.text = movies.release_date
                            mainBinding.dLanguage.text = movies.original_language
                            mainBinding.dRating.rating = (movies.vote_average / 2).toFloat()
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