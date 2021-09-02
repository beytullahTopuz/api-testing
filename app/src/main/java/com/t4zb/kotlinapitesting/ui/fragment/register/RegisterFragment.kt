package com.t4zb.kotlinapitesting.ui.fragment.register

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
import com.t4zb.kotlinapitesting.databinding.FragmentRegisterBinding
import com.t4zb.kotlinapitesting.ui.contract.BaseContract
import com.t4zb.kotlinapitesting.ui.fragment.basefragment.BaseFragment
import com.t4zb.kotlinapitesting.ui.presenter.BasePresenter


class RegisterFragment : BaseFragment(R.layout.fragment_register), BaseContract.ViewMain {

    private lateinit var mContext: FragmentActivity

    private val mViewModel: RegisterViewModel by viewModels()

    private lateinit var mainBinding: FragmentRegisterBinding

    private lateinit var firebaseAuth: FirebaseAuth

    private val mPresenter: BasePresenter by lazy {
        BasePresenter(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainBinding = FragmentRegisterBinding.bind(view)
        mPresenter.onViewsCreated()
        firebaseAuth = FirebaseAuth.getInstance()


        mViewModel.registerViewState.observe(viewLifecycleOwner,{ registerViewState->

            render(registerViewState)
        })


        mainBinding.signUpBtnSignUp.setOnClickListener {

            mViewModel.btn_register_onclick(firebaseAuth)
        }
        mainBinding.signUpWithGoogle.setOnClickListener{

            mViewModel.btn_register_with_google(firebaseAuth)
        }

        mViewModel.str_name = mainBinding.signUpTextName.text;
        mViewModel.str_surname = mainBinding.signUpTextSurname.text;
        mViewModel.str_email = mainBinding.signUpEmail.text;
        mViewModel.str_password = mainBinding.signUpTextPassword.text;
        mViewModel.str_passwordConfirm = mainBinding.signUpTextPasswordConfirm.text;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()

    }


    private fun render(viewState:RegisterViewState) {
        if (viewState.isLoading == true){
            mainBinding.registerProgressBar.visibility = View.VISIBLE
        }else{
            mainBinding.registerProgressBar.visibility = View.INVISIBLE
        }

        if (viewState.isFail == true){
            Toast.makeText(mContext,viewState.errorMsg, Toast.LENGTH_LONG).show()
        }

        if (viewState.isSuccess == true){
            Toast.makeText(mContext,"register successful", Toast.LENGTH_LONG).show()
        }

    }

    companion object {
    }

    override fun setupViewModel() {

    }

    override fun initializeViews() {

    }
}