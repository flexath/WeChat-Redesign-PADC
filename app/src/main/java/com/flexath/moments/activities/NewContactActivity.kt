package com.flexath.moments.activities

import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.flexath.moments.databinding.ActivityNewContactBinding
import com.flexath.moments.databinding.ActivityNewMomentBinding
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions


class NewContactActivity : AppCompatActivity() {

    private lateinit var binding:ActivityNewContactBinding

    private lateinit var barcodeLauncher:ActivityResultLauncher<ScanOptions>

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, NewContactActivity::class.java)
        }
    }

    override fun onStart() {
        super.onStart()
        // Register the launcher and result handler
        barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Log.i("ResultQR",result.barcodeImagePath.toString())
                Log.i("ResultQRContents",result.contents.toString())

                val barcodeEncoder = BarcodeEncoder()
                val bitmap = barcodeEncoder.encodeBitmap(result.contents, BarcodeFormat.QR_CODE, 400, 400)
                binding.ivScanImage.setImageBitmap(bitmap)

                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewContactBinding.inflate(layoutInflater)
        setContentView(binding.root)


       binding.ivScanImage.setOnClickListener {
           setUpQRCode()
       }
    }

    private fun setUpQRCode() {
        val options = ScanOptions()
        options.setBarcodeImageEnabled(true)
        options.setPrompt("Scan QR Code")
        options.setOrientationLocked(false)
        barcodeLauncher.launch(options)
    }
}