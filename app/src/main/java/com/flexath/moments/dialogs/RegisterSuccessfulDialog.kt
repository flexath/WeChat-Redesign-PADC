package com.flexath.moments.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager

@Suppress("DEPRECATION")
class RegisterSuccessfulDialog(context: Context) : Dialog(context, android.R.style.Theme_DeviceDefault_Light_Dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.ownerActivity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val windowParams = window?.attributes
        val density = context.resources.displayMetrics.density
        windowParams?.width = (350 * density).toInt()
        windowParams?.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes = windowParams
    }
}