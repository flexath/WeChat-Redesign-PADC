@file:Suppress("DEPRECATION")

package com.flexath.moments.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.databinding.ActivityNewContactBinding
import com.flexath.moments.mvp.impls.NewContactPresenterImpl
import com.flexath.moments.mvp.interfaces.NewContactPresenter
import com.flexath.moments.mvp.views.NewContactView
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.CaptureActivity

class CaptureActivityPortrait : CaptureActivity()

class NewContactActivity : AppCompatActivity(), NewContactView {

    private lateinit var binding: ActivityNewContactBinding

    // Presenters
    private lateinit var mPresenter: NewContactPresenter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, NewContactActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewContactBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPresenter()

        binding.ivScanImage.setOnClickListener {
            setUpQRCode()
        }
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[NewContactPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                mPresenter.getUsers(result.contents)
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun setUpQRCode() {
        val integrator = IntentIntegrator(this)
        integrator.setPrompt("Scan a QR Code")
        integrator.setCameraId(0) // Use a specific camera of the device

        integrator.setOrientationLocked(true)
        integrator.setBeepEnabled(true)
        integrator.captureActivity = CaptureActivityPortrait::class.java
        integrator.initiateScan()
    }

    override fun getUsers(userList: List<UserVO>, qrExporterUserId: String) {
        outer@ for (user in userList) {
            if (mPresenter.getScannerUserId() == user.userId) {
                for (exportUser in userList) {
                    if (qrExporterUserId == exportUser.userId) {
                        mPresenter.createContact(
                            mPresenter.getScannerUserId(),
                            qrExporterUserId,
                            exportUser
                        )
                        break@outer
                    }
                }
            }
        }

        outer@ for (user in userList) {
            if (qrExporterUserId == user.userId) {
                for (scannerUser in userList) {
                    if (mPresenter.getScannerUserId() == scannerUser.userId) {
                        mPresenter.createContact(
                            qrExporterUserId,
                            mPresenter.getScannerUserId(),
                            scannerUser
                        )
                        break@outer
                    }
                }
            }
        }
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}