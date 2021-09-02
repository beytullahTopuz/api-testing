package com.t4zb.kotlinapitesting.ui.fragment.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.databinding.FragmentLoginBinding
import com.t4zb.kotlinapitesting.ui.contract.BaseContract
import com.t4zb.kotlinapitesting.ui.fragment.basefragment.BaseFragment
import com.t4zb.kotlinapitesting.ui.presenter.BasePresenter

class LoginFragment : BaseFragment(R.layout.fragment_login), BaseContract.ViewMain {

    private lateinit var mContext: FragmentActivity

    private val mViewModel: LoginViewModel by viewModels()

    private lateinit var mBinding: FragmentLoginBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private val mPresenter: BasePresenter by lazy {
        BasePresenter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = FragmentLoginBinding.bind(view)
        mPresenter.onViewsCreated()
        firebaseAuth = FirebaseAuth.getInstance()

        mViewModel.loginViewState.observe(viewLifecycleOwner) { loginViewState ->
            render(loginViewState)
        }

        mBinding.loginBtn.setOnClickListener {
            mViewModel.btn_login_onclick(firebaseAuth)
        }
        mViewModel.str_email = mBinding.loginEmailTextField.text
        mViewModel.str_password = mBinding.loginPasswordTextField.text
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()
    }

    private fun render(viewState: LoginViewState){
        if (viewState.isSuccess){
            Toast.makeText(mContext,"Login Successful", Toast.LENGTH_LONG).show()
        }
        if (viewState.isFail){
            Toast.makeText(mContext,viewState.errorMsg.toString(), Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val TAG = "LoginFragment"
    }

    override fun setupViewModel() {

    }

    override fun initializeViews() {

    }
}