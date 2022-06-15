package com.example.scorpcase.base

import android.app.Dialog
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.scorpcase.R

open class BaseActivity : AppCompatActivity() {

    var loadingDialog: Dialog? = null

    private fun getProgressDialog() = loadingDialog ?: try {
        Dialog(this, R.style.Theme_ScorpCase).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.progress)
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
    } catch (e: Exception) {
        null
    }

    fun manageProgressDialogVisibility(show: Boolean) {
        if (show) showProgressDialog() else dismissProgressDialog()
    }

    fun showProgressDialog() {
        if (isFinishing) {
            dismissProgressDialog()
            return
        }

        loadingDialog = getProgressDialog()
        loadingDialog?.let {
            if (!it.isShowing && !isFinishing) try {
                it.show()
            } catch (e: Exception) {
                loadingDialog = null
            }
        }
    }

    fun dismissProgressDialog() {
        if (loadingDialog == null) return

        try {
            loadingDialog?.dismiss()
        } catch (ignored: Exception) {
        }
        loadingDialog = null
    }
}