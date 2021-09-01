package com.t4zb.kotlinapitesting.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.material.appbar.AppBarLayout
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.databinding.FragmentUserProfileBinding
import com.t4zb.kotlinapitesting.ui.contract.BaseContract
import com.t4zb.kotlinapitesting.ui.fragment.basefragment.BaseFragment
import com.t4zb.kotlinapitesting.ui.presenter.BasePresenter
import com.t4zb.kotlinapitesting.util.ViewUtils
import com.t4zb.kotlinapitesting.util.showLogDebug
import kotlin.math.abs


class UserProfileFragment : BaseFragment(R.layout.fragment_user_profile), BaseContract.ViewMain,
    AppBarLayout.OnOffsetChangedListener {
    private lateinit var mContext: FragmentActivity
    private lateinit var mBinding: FragmentUserProfileBinding

    private val mPresenter: BasePresenter by lazy {
        BasePresenter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentUserProfileBinding.bind(view)
        mPresenter.onViewsCreated()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mContext = requireActivity()
        super.onCreate(savedInstanceState)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        val maxScroll = mBinding.appBarProfile.totalScrollRange
        val perc = abs(verticalOffset).toFloat() / maxScroll.toFloat()
        showLogDebug(TAG, "" + perc)
        if (perc >= .9f) {
            mBinding.toolbarProfile.visibility = View.VISIBLE
            mBinding.toolbarBannerProfile.visibility = View.INVISIBLE
            mBinding.toolbarProfile.background =
                ContextCompat.getDrawable(mContext, R.color.white)
        } else if (perc < .9f) {
            mBinding.toolbarProfile.visibility = View.INVISIBLE
            mBinding.toolbarBannerProfile.visibility = View.VISIBLE
            mBinding.toolbarProfile.background = ContextCompat.getDrawable(mContext, R.color.transparent)
        }
    }

    override fun setupViewModel() {

    }

    override fun initializeViews() {
        mBinding.appBarProfile.post {
            val height = ViewUtils.getScreenWidth(mContext) * 1 / 3
            setOffset(height)
        }
    }

    fun setOffset(offsetPx: Int) {
        val params = mBinding.appBarProfile.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as AppBarLayout.Behavior
        behavior.onNestedPreScroll(
            mBinding.root,
            mBinding.appBarProfile,
            mBinding.fragNestedLytProfile,
            0,
            offsetPx,
            intArrayOf(0, 0)
        )
    }

    companion object{
        private const val TAG = "UserProfileFragment"
    }
}