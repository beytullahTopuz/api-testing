package com.t4zb.kotlinapitesting.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.databinding.FragmentDetailBinding
import com.t4zb.kotlinapitesting.helper.PicassoHelper
import com.t4zb.kotlinapitesting.ui.contract.BaseContract
import com.t4zb.kotlinapitesting.ui.fragment.basefragment.BaseFragment
import com.t4zb.kotlinapitesting.ui.viewmodel.SharedViewModel


class DetailFragment : BaseFragment(R.layout.fragment_detail) , BaseContract.ViewMain{

    private lateinit var mContext: FragmentActivity

    private lateinit var mSharedViewModel: SharedViewModel


    private lateinit var mainBinding: FragmentDetailBinding




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        mainBinding = FragmentDetailBinding.bind(view)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    mContext = requireActivity()

    }





     override fun onCreateView(
          inflater: LayoutInflater, container: ViewGroup?,
          savedInstanceState: Bundle?
      ): View? {
          // Inflate the layout for this fragment
          return inflater.inflate(R.layout.fragment_detail, container, false)
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



        mSharedViewModel.selectedMoviePop.observe(viewLifecycleOwner, Observer {


            if (it != null){
                mainBinding.textViewTitle.text = it.title

                mainBinding.textViewOverview.text = it.overview

                PicassoHelper.picassoOkhttp(mContext,it.poster_path,mainBinding.imageViewPoster)

                PicassoHelper.picassoOkhttp(mContext,it.backdrop_path,mainBinding.imageViewBackdrop)
            }

        })




    }


}