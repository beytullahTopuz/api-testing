package com.t4zb.kotlinapitesting.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.appUser.AppUser
import com.t4zb.kotlinapitesting.databinding.FragmentFavoriteBinding
import com.t4zb.kotlinapitesting.helper.GmsFavoriteHelper
import com.t4zb.kotlinapitesting.helper.SwipeControlActions
import com.t4zb.kotlinapitesting.ui.contract.BaseContract
import com.t4zb.kotlinapitesting.ui.fragment.basefragment.BaseFragment
import com.t4zb.kotlinapitesting.ui.presenter.BasePresenter
import com.t4zb.kotlinapitesting.util.showToast


class FavoriteFragment : BaseFragment(R.layout.fragment_favorite), BaseContract.ViewMain {
    private lateinit var mContext: FragmentActivity
    private lateinit var mBinding: FragmentFavoriteBinding
    private var pos: Int = 0
    private var keyHolder = ArrayList<String>()

    private val mPresenter: BasePresenter by lazy {
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
        if (AppUser.getFirebaseUser() == null) {
            showToast(mContext, "please login..")
        } else {
            GmsFavoriteHelper.setFirebaseRecycler(mBinding.moviesFavoriteRecyclerView)

            val swipeCardView = object : SwipeControlActions(mContext) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val adapter = mBinding.moviesFavoriteRecyclerView.adapter
                    pos = viewHolder.absoluteAdapterPosition
                    GmsFavoriteHelper.deleteFavorites(pos)
                    adapter!!.notifyItemRemoved(viewHolder.absoluteAdapterPosition)
                }
            }
            val itemTouchHelper = ItemTouchHelper(swipeCardView)
            itemTouchHelper.attachToRecyclerView(mBinding.moviesFavoriteRecyclerView)
        }
    }
}