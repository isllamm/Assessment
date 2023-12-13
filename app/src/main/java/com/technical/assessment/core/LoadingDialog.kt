package com.technical.assessment.core

import android.app.Dialog
import android.content.Context
import com.technical.assessment.R
import com.technical.assessment.databinding.DialogLoadingBinding

class LoadingDialog(context: Context) : Dialog(context) {

    fun showLoading() {
        show()
    }

    fun hideLoading() {
        dismiss()
        cancel()
    }

    init {
        val binding = DialogLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCancelable(false)
        window!!.setBackgroundDrawableResource(R.color.transparent)
    }
}