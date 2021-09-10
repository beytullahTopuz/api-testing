package com.t4zb.kotlinapitesting.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.databinding.FragmentFavoriteBinding
import com.t4zb.kotlinapitesting.helper.FirebaseDbHelper
import com.t4zb.kotlinapitesting.helper.GmsFavoriteHelper
import com.t4zb.kotlinapitesting.ui.contract.BaseContract
import com.t4zb.kotlinapitesting.ui.fragment.basefragment.BaseFragment
import com.t4zb.kotlinapitesting.ui.presenter.BasePresenter


class FavoriteFragment : BaseFragment(R.layout.fragment_favorite), BaseContract.ViewMain {
    private lateinit var  mContext: FragmentActivity

    private lateinit var mBinding : FragmentFavoriteBinding

    private val mPresenter : BasePresenter by lazy {
        BasePresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentFavoriteBinding.bind(view)
        mPresenter.onViewsCreated()
    }


    companion object {
        private const val TAG = "FavoriteFragment"
    }

    override fun setupViewModel() {

    }

    override fun initializeViews() {
        GmsFavoriteHelper.setFirebaseRecycler(mBinding.moviesFavoriteRecyclerView)
    }
}