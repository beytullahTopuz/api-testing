package com.t4zb.kotlinapitesting.ui.fragment.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.t4zb.kotlinapitesting.R
import com.t4zb.kotlinapitesting.databinding.FragmentLoginBinding
import com.t4zb.kotlinapitesting.helper.GmsLoginHelper
import com.t4zb.kotlinapitesting.ui.contract.BaseContract
import com.t4zb.kotlinapitesting.ui.contract.LoginHelper
import com.t4zb.kotlinapitesting.ui.fragment.basefragment.BaseFragment
import com.t4zb.kotlinapitesting.ui.presenter.LoginPresenter

class LoginFragment : BaseFragment(R.layout.fragment_login), BaseContract.ViewMain {

    private lateinit var mContext: FragmentActivity

    private val mViewModel: LoginViewModel by viewModels()

    private lateinit var mBinding: FragmentLoginBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private val gLoginHelper: LoginHelper by lazy {
        GmsLoginHelper(mContext)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding = FragmentLoginBinding.bind(view)

        firebaseAuth = FirebaseAuth.getInstance()

        mViewModel.loginViewState.observe(viewLifecycleOwner) { loginViewState ->
            render(loginViewState)
        }

        mBinding.loginBtn.setOnClickListener {
            mViewModel.str_email = mBinding.loginEmailTextField.text
            mViewModel.str_password = mBinding.loginPasswordTextField.text
            mViewModel.btn_login_onclick(firebaseAuth)
        }

        mBinding.loginUpWithGoogleBtn.setOnClickListener {

        }

        mBinding.registerBtn.setOnClickListener {

            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        mBinding.loginEmailTextField.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                //
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        gLoginHelper.onDataReceived(requestCode, resultCode, data)
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