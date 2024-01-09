package com.sinansarisen.mvvm_template.utils

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.sinansarisen.mvvm_template.R
import com.sinansarisen.mvvm_template.databinding.PasswordAlertDialogBinding

 fun showErrorInternet (activty: FragmentActivity, context: Context) {
    activty.runOnUiThread {

        var alertDialog = PasswordAlertDialogBinding.inflate(activty.layoutInflater)
        var mBuilder = AlertDialog.Builder(context, R.style.CustomMaterialDialog).create()
        mBuilder.setView(alertDialog.root)

        alertDialog.textTryAgain.text = "Lütfen daha sonra tekrar deneyin."
        alertDialog.textErrorMessage.text = "Teknik bir aksaklıktan dolayı devam edilemiyor. "
        mBuilder.setCancelable(false)
        mBuilder.show()

        var alertDialogInstance = mBuilder

        alertDialog.buttonClose.setOnClickListener {
            alertDialogInstance.dismiss()
            ActivityCompat.finishAffinity(activty)
            System.exit(0)
        }
        alertDialog.buttonEnter.setOnClickListener {
            ActivityCompat.finishAffinity(activty)
            System.exit(0)
        }
    }
}


fun isInternetConnected(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    return activeNetwork?.isConnectedOrConnecting == true
}