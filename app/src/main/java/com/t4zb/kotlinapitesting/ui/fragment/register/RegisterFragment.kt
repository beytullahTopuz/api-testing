package com.t4zb.kotlinapitesting.ui.fragment.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.databinding.FragmentRegisterBinding
import com.t4zb.kotlinapitesting.helper.GmsLoginHelper
import com.t4zb.kotlinapitesting.ui.contract.LoginHelper
import com.t4zb.kotlinapitesting.ui.fragment.basefragment.BaseFragment
import com.t4zb.kotlinapitesting.ui.presenter.LoginPresenter


class RegisterFragment : BaseFragment(R.layout.fragment_register), LoginPresenter.LoginContract {

    private lateinit var mContext: FragmentActivity

    private val mViewModel: RegisterViewModel by viewModels()

    private lateinit var mainBinding: FragmentRegisterBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private val loginHelperBuild: GmsLoginHelper by lazy {
        GmsLoginHelper(mContext)
    }

    private val gLoginHelper: LoginHelper by lazy {
        GmsLoginHelper(mContext)
    }

    private val presenter: LoginPresenter by lazy { LoginPresenter(this, gLoginHelper) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainBinding = FragmentRegisterBinding.bind(view)
        presenter.onViewsCreate()
        firebaseAuth = FirebaseAuth.getInstance()


        mViewModel.registerViewState.observe(viewLifecycleOwner, { registerViewState ->

            render(registerViewState)
        })


        mainBinding.signUpBtnSignUp.setOnClickListener {

            mViewModel.str_name = mainBinding.signUpTextName.text
            mViewModel.str_email = mainBinding.signUpEmail.text
            mViewModel.str_password = mainBinding.signUpTextPassword.text
            mViewModel.str_passwordConfirm = mainBinding.signUpTextPasswordConfirm.text
            mViewModel.btn_register_onclick(firebaseAuth)
            presenter.onEmailClick()
        }
        mainBinding.signUpWithGoogle.setOnClickListener {

            //mViewModel.btn_register_with_google(firebaseAuth)
            presenter.onSignInGClick()
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()

    }


    private fun render(viewState: RegisterViewState) {
        if (viewState.isLoading) {
            mainBinding.registerProgressBar.visibility = View.VISIBLE
        } else {
            mainBinding.registerProgressBar.visibility = View.INVISIBLE
        }

        if (viewState.isFail) {
            Toast.makeText(mContext, viewState.errorMsg, Toast.LENGTH_LONG).show()
        }

        if (viewState.isSuccess) {
            Toast.makeText(mContext, "register successful", Toast.LENGTH_LONG).show()
        }

    }

    companion object {
        private const val TAG = "RegisterFragment"
    }

    override fun redirectToUserProfile() {
        // Navigation
        findNavController().navigate(R.id.action_registerFragment_to_profileFragment)
    }

    override fun restartApp() {

    }

    override fun redirectToSignIn(signInIntent: Intent, requestCode: Int) {
        startActivityForResult(signInIntent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        gLoginHelper.onDataReceived(requestCode, resultCode, data)
    }

    override fun initializeViews() {

    }
}