package com.flexath.moments.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager


@Suppress("DEPRECATION")
class EditProfileDialog(context: Context) : Dialog(context, android.R.style.Theme_DeviceDefault_Light_Dialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.ownerActivity?.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val windowParams = window?.attributes
        windowParams?.width = WindowManager.LayoutParams.MATCH_PARENT
        windowParams?.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes = windowParams

//        val density = context.resources.displayMetrics.density
//        (heightInDp * density).toInt()
    }
}