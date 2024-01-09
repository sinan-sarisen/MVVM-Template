package com.sinansarisen.mvvm_template.ui

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sinansarisen.mvvm_template.R
import com.sinansarisen.mvvm_template.databinding.FragmentLoginBinding
import com.sinansarisen.mvvm_template.databinding.PasswordAlertDialogBinding
import com.sinansarisen.mvvm_template.models.LoginRequestModel
import com.sinansarisen.mvvm_template.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LoginViewModel

    private lateinit var userName: String
    private lateinit var password: String
    private lateinit var ssid: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        val view = binding.root

        initLogin()
        setObserver()
        return view
    }

    private fun setObserver() {
        viewModel.login.observe(viewLifecycleOwner) {
            if (it.isSuccess) {
                val sharedPref: SharedPreferences =
                    requireContext().getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
                sharedPref.edit().putString("userName", userName).apply()

                ssid = it.ssId

            }
            else
            {
                showErrorAlert("userError")
                binding.buttonEnter.isEnabled=true
                binding.dataLoading.visibility=View.GONE

            }


        }
        viewModel.loginError.observe(viewLifecycleOwner) {

            binding.dataLoading.visibility= View.GONE
            binding.buttonEnter.isEnabled = true;
            showErrorAlert("loginError")
        }


        viewModel.dataLoading.observe(viewLifecycleOwner)
        {
            if(it)
            {
                binding.dataLoading.visibility=View.GONE
                binding.buttonEnter.isEnabled=true
            }
        }

    }

    private fun showErrorAlert(errorType: String) {

        var alertDialog = PasswordAlertDialogBinding.inflate(layoutInflater)
        val mBuilder = AlertDialog.Builder(requireContext(), R.style.CustomMaterialDialog).create()

        mBuilder.setView(alertDialog.root)
        mBuilder.setCancelable(false);
        mBuilder.show()

        if(errorType=="loginError")
        {
            alertDialog.textErrorMessage.text="Teknik bir nedenden dolayı işleminize devam edilemiyor."
        }

        var alertDialogInstance = mBuilder

        alertDialog.buttonClose.setOnClickListener {
            alertDialogInstance.dismiss()
        }
        alertDialog.buttonEnter.setOnClickListener {
            alertDialogInstance.dismiss()
        }
    }

    private fun initLogin() {

        binding.buttonEnter.setOnClickListener {

            binding.dataLoading.visibility=View.VISIBLE
            binding.buttonEnter.isEnabled=false
            userName = binding.userNameEditText.text.toString()
            password = binding.passwordEditText.text.toString()

            var loginRequestModel= LoginRequestModel(userName,password)
            viewModel.getLogin(loginRequestModel)
        }
    }
}