package com.sinansarisen.mvvm_template.ui

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sinansarisen.mvvm_template.R
import com.sinansarisen.mvvm_template.databinding.FragmentSplashBinding
import com.sinansarisen.mvvm_template.databinding.PasswordAlertDialogBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    //private lateinit var mBuilder : AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.versiyonText.text = getVersionName()


        Handler().postDelayed({
            GlobalScope.launch {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }
        },3500)


        return view

    }

    private fun getVersionName(): String {

        val manager = requireContext().packageManager
        val info = manager.getPackageInfo(
            requireContext().packageName, 0
        )
        return "Versiyon" + info.versionName
    }


    private fun showErrorAlert() {
        requireActivity().runOnUiThread {
            var alertDialog = PasswordAlertDialogBinding.inflate(layoutInflater)
            var mBuilder =  AlertDialog.Builder(requireContext(), R.style.CustomMaterialDialog).create()
            mBuilder.setView(alertDialog.root)
            alertDialog.textTryAgain.text = "Lütfen daha sonra tekrar deneyin."
            alertDialog.textErrorMessage.text = "Teknik bir aksaklıktan dolayı devam edilemiyor. "
            mBuilder.setCancelable(false)
            mBuilder.show()

            var alertDialogInstance = mBuilder

            alertDialog.buttonClose.setOnClickListener {
                alertDialogInstance.dismiss()
                requireActivity().finish()
            }
            alertDialog.buttonEnter.setOnClickListener {
                alertDialogInstance.dismiss()
                requireActivity().finish()
            }
        }
    }


}