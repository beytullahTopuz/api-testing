package com.t4zb.kotlinapitesting.ui.fragment.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.google.android.material.appbar.AppBarLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.appUser.AppUser
import com.t4zb.kotlinapitesting.databinding.FragmentUserProfileBinding
import com.t4zb.kotlinapitesting.helper.FirebaseDbHelper
import com.t4zb.kotlinapitesting.helper.PicassoHelper
import com.t4zb.kotlinapitesting.ui.contract.BaseContract
import com.t4zb.kotlinapitesting.ui.fragment.basefragment.BaseFragment
import com.t4zb.kotlinapitesting.ui.presenter.BasePresenter
import com.t4zb.kotlinapitesting.util.FirebaseConstants
import com.t4zb.kotlinapitesting.util.ViewUtils
import com.t4zb.kotlinapitesting.util.showLogDebug
import com.t4zb.kotlinapitesting.util.showLogError
import kotlin.math.abs


class UserProfileFragment : BaseFragment(R.layout.fragment_user_profile), BaseContract.ViewMain,
    AppBarLayout.OnOffsetChangedListener {
    private lateinit var mContext: FragmentActivity
    private lateinit var mBinding: FragmentUserProfileBinding
    private val mViewModel: ProfileViewModel by viewModels()
    private lateinit var firebaseAuth: FirebaseAuth
    private val mPresenter: BasePresenter by lazy {
        BasePresenter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = FragmentUserProfileBinding.bind(view)
        mPresenter.onViewsCreated()
        mViewModel.getUserToDB(firebaseAuth)
        firebaseAuth = FirebaseAuth.getInstance()

        mBinding.signOutBtn.setOnClickListener {
            mViewModel.singOut(firebaseAuth)
        }
        mBinding.profileImageView.setOnClickListener {
            //update profile image on firabase
          /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    //permission already granted
                    pickImageFromGallery()
                }
            } else {
                //system OS is < Marshmallow
                pickImageFromGallery()
            }
            */
        }
        render()
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }


    private fun renderImage(imageURL: String) {
        showLogDebug(TAG, imageURL)
        if (imageURL != "") {
            PicassoHelper.picassoUtils(mContext, imageURL, mBinding.profileImageView)
        }
    }


    private fun render() {
        val firebaseDB = FirebaseDbHelper.getUserInfo(AppUser.getFirebaseUser()!!.uid)
        firebaseDB.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child(FirebaseConstants.PATH_NAME).value.toString()
              //  val surname = snapshot.child(FirebaseConstants.PATH_SURNAME).value.toString()
                val avatar = snapshot.child(FirebaseConstants.PATH_AVATAR).value.toString()
                val email = snapshot.child(FirebaseConstants.PATH_EMAIL).value.toString()

                renderImage(avatar)
                mBinding.emailTextField.setText(email)
                mBinding.userNameTextField.setText(name)

            }

            override fun onCancelled(error: DatabaseError) {
                showLogError(TAG, error.toString())
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mContext = requireActivity()
        firebaseAuth = FirebaseAuth.getInstance()
        mViewModel.getUserEmail(firebaseAuth)
        super.onCreate(savedInstanceState)
    }

  /*  override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
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
            mBinding.toolbarProfile.background =
                ContextCompat.getDrawable(mContext, R.color.transparent)
        }
    }
    */

    override fun setupViewModel() {

    }

    override fun initializeViews() {
     /*   mBinding.appBarProfile.post {
            val height = ViewUtils.getScreenWidth(mContext) * 1 / 3
            setOffset(height)
        }
        */
    }

  /*  fun setOffset(offsetPx: Int) {
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
    */

    companion object {
        private const val TAG = "UserProfileFragment"

        //image pick code
        private const val IMAGE_PICK_CODE = 1000

        //Permission code
        private const val PERMISSION_CODE = 1001
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {

    }
}