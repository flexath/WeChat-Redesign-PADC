package com.flexath.moments.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color.BLACK
import android.graphics.Color.WHITE
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.flexath.moments.R
import com.flexath.moments.data.vos.MomentVO
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.databinding.BottomSheetDialogMomentOptionBinding
import com.flexath.moments.databinding.BottoomSheetDialogChooseImageBinding
import com.flexath.moments.databinding.DialogEditProfileBinding
import com.flexath.moments.databinding.DialogQrCodeBinding
import com.flexath.moments.databinding.FragmentProfileBinding
import com.flexath.moments.dialogs.EditProfileDialog
import com.flexath.moments.mvp.impls.ProfilePresenterImpl
import com.flexath.moments.mvp.interfaces.ProfilePresenter
import com.flexath.moments.mvp.views.ProfileView
import com.flexath.moments.views.viewpods.MomentViewPod
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.io.IOException
import java.util.Calendar

@Suppress("DEPRECATION")
class ProfileFragment : Fragment() , ProfileView {

    private lateinit var binding:FragmentProfileBinding

    // Presenters
    private lateinit var mPresenter: ProfilePresenter

    // ViewPods
    private lateinit var mViewpod:MomentViewPod

    // General
    private var gender:String = ""
    private var email:String = ""
    private var password:String = ""
    private var qrCode:String = ""
    private var imageUrl:String = ""
    private var day:String = ""
    private var month:String = ""
    private var year:String = ""

    private val REQUEST_CODE_GALLERY = 0
    private val REQUEST_IMAGE_CAPTURE = 1
    private var bitmap:Bitmap? = null
    private var mUser:UserVO? = null
    private var mMomentList:ArrayList<MomentVO> = arrayListOf()
    private lateinit var dialog:BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenter()
        setUpViewPods()
        setUpListeners()

        mPresenter.onUIReady(this)
        mPresenter.getMomentsFromUserBookmarked(mPresenter.getUserId())
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[ProfilePresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpViewPods() {
        mViewpod = binding.vpPostProfile.root
        mViewpod.setDelegate(mPresenter)
    }

    private fun setUpListeners() {
        binding.btnEditProfile.setOnClickListener {
            mPresenter.onTapEditProfileButton()
        }

        binding.ivQrCodeProfile.setOnClickListener {
            mPresenter.onTapQrCodeImage()
        }

        dialog = BottomSheetDialog(requireActivity())

        binding.ivUpdatePictureProfile.setOnClickListener {

            val dialogBinding = BottoomSheetDialogChooseImageBinding.inflate(layoutInflater)

            dialog.setContentView(dialogBinding.root)
            dialog.setCancelable(true)

            dialogBinding.btnTakePhotoRegister.setOnClickListener {
                mPresenter.onTapOpenCameraButton()
            }

            dialogBinding.btnChooseFromGalleryRegister.setOnClickListener {
                mPresenter.onTapGalleryImage()
            }

            dialogBinding.btnCancelBottomSheetDialog.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }


    @Throws(WriterException::class)
    fun textToImageEncode(Value: String?): Bitmap? {
        val bitMatrix: BitMatrix
        try {
            bitMatrix = MultiFormatWriter().encode(
                Value,
                BarcodeFormat.QR_CODE,
                100, 100, null
            )
        } catch (IllegalArgumentException: IllegalArgumentException) {
            return null
        }
        val bitMatrixWidth = bitMatrix.width
        val bitMatrixHeight = bitMatrix.height
        val pixels = IntArray(bitMatrixWidth * bitMatrixHeight)
        for (y in 0 until bitMatrixHeight) {
            val offset = y * bitMatrixWidth
            for (x in 0 until bitMatrixWidth) {
                pixels[offset + x] = if (bitMatrix[x, y]) BLACK else WHITE
            }
        }
        val bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444)
        bitmap.setPixels(pixels, 0, 100, 0, 0, bitMatrixWidth, bitMatrixHeight)
        return bitmap
    }

    private fun getYearsForYearSpinner() : ArrayList<String> {
        val years = arrayListOf("Year")
        val thisYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        for (year in 1900..thisYear) {
            years.add(year.toString())
        }
        return years
    }

    override fun showUserInformation(userList: List<UserVO>) {
        for(user in userList) {
            if(mPresenter.getUserId() == user.userId) {

                mUser = user

                binding.tvNameProfile.text = user.userName
                binding.tvPhoneNumberProfile.text = user.phoneNumber
                binding.tvDateProfile.text = user.birthDate
                binding.tvGenderProfile.text = user.gender

                email = user.email
                password = user.password
                qrCode = user.qrCode
                imageUrl = user.imageUrl

                Glide.with(requireActivity())
                    .load(user.imageUrl)
                    .into(binding.ivProfileImageProfile)

                binding.ivQrCodeProfile.setImageBitmap(textToImageEncode(user.qrCode))
            }
        }
    }

    override fun showEditProfileDialog() {
        val dialogBinding = DialogEditProfileBinding.inflate(layoutInflater)
        val dialog = EditProfileDialog(requireActivity())

        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(true)

        val adapter = ArrayAdapter(requireActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, getYearsForYearSpinner())
        dialogBinding.yearSpinnerEditProfile.adapter = adapter

        dialogBinding.etNameEditProfile.setText(binding.tvNameProfile.text.toString())
        dialogBinding.etPhoneNumberEditProfile.setText(binding.tvPhoneNumberProfile.text.toString())

        setUpGenderRadioButtons(dialogBinding)
        setUpDateOfBirthSpinners(dialogBinding)

        dialogBinding.btnSaveDialog.setOnClickListener {
            val userName = dialogBinding.etNameEditProfile.text.toString()
            val phoneNumber = dialogBinding.etPhoneNumberEditProfile.text.toString()
            val birthDate = "$year-$month-$day"
            val user = UserVO(mPresenter.getUserId(),userName,phoneNumber,email, password,birthDate,gender, qrCode, imageUrl)
            mPresenter.updateUserInformation(user)
            dialog.dismiss()
        }

        dialogBinding.btnCancelDialog.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun showQrCodeDialog() {
        val dialogBinding = DialogQrCodeBinding.inflate(layoutInflater)
        val dialog = Dialog(requireActivity(), R.style.TransparentDialogTheme)

        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(false)

        dialogBinding.btnCloseProfileDialog.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.ivQRCodeProfileDialog.setImageBitmap(textToImageEncode(qrCode))

        dialog.show()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ((requestCode == REQUEST_CODE_GALLERY || requestCode == REQUEST_IMAGE_CAPTURE) && resultCode == Activity.RESULT_OK) {
//            if (data == null || data.data == null) {
//                return
//            }

            val filePath = data?.data

            if(requestCode == REQUEST_IMAGE_CAPTURE) {
                Toast.makeText(requireActivity(), "You take a photo", Toast.LENGTH_SHORT).show()
                val imageBitmap = data?.extras?.get("data") as Bitmap
                bitmap = imageBitmap
                bitmap?.let { image ->
                    mUser?.let {user ->
                        mPresenter.updateAndUploadProfileImage(image, user)
                    }
                }
                dialog.dismiss()
                return
            }

            try {
                filePath?.let { fileUrl ->
                    if (Build.VERSION.SDK_INT >= 29) {
                        val source = ImageDecoder.createSource(requireActivity().contentResolver, fileUrl)
                        val bitmapImage = ImageDecoder.decodeBitmap(source)
                        bitmap = bitmapImage
                        bitmap?.let { image ->
                            mUser?.let {user ->
                                mPresenter.updateAndUploadProfileImage(image, user)
                            }
                        }

                    } else {
                        val bitmapImage = MediaStore.Images.Media.getBitmap(
                            context?.applicationContext?.contentResolver, fileUrl
                        )
                        bitmap = bitmapImage
                        bitmap?.let { image ->
                            mUser?.let {user ->
                                mPresenter.updateAndUploadProfileImage(image, user)
                            }
                        }
                    }
                }
                dialog.dismiss()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun showGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Upload Image"), REQUEST_CODE_GALLERY)
    }

    @SuppressLint("QueryPermissionsNeeded")
    override fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(
            Intent.createChooser(intent, "Select Upload Image"),
            REQUEST_IMAGE_CAPTURE
        )
    }

    override fun showMoments(momentList: List<MomentVO>) {
        mMomentList = momentList as ArrayList<MomentVO>
        mViewpod.setNewData(momentList, "profile")
    }

    override fun getMomentIsBookmarked(id: String, bookmarked:Boolean) {
        for(moment in mMomentList) {
            if(id == moment.id) {
                mMomentList.remove(moment)
                mPresenter.deleteMomentFromUserBookmarked(mPresenter.getUserId(),id)
                break
            }
        }
        mViewpod.setNewData(mMomentList, "profile")
    }

    override fun showOptionDialogBox(momentId:String,momentOwnerUserId:String) {
        val dialogBinding = BottomSheetDialogMomentOptionBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(true)

        dialogBinding.btnEditMoment.setOnClickListener {

        }

        dialogBinding.btnDeleteMoment.setOnClickListener {
            val dialogDeleteBox =
                MaterialAlertDialogBuilder(requireActivity(), R.style.RoundedAlertDialog)
                    .setTitle("Delete Moment ?")
                    .setMessage("Are you sure to delete ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { deleteDialog, _ ->

                    }
                    .setNegativeButton("Cancel") { deleteDialog, _ ->
                        deleteDialog?.dismiss()
                    }
                    .create()
            dialogDeleteBox.show()
        }

        dialog.show()
    }

    private fun setUpDateOfBirthSpinners(dialogBinding:DialogEditProfileBinding) {
        dialogBinding.daySpinnerEditProfile.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapter: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position > 0) {
                    day = adapter?.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        dialogBinding.monthSpinnerEditProfile.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapter: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                if(position > 0) {
                    month = adapter?.getItemAtPosition(position).toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        dialogBinding.yearSpinnerEditProfile.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapter: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                if(position > 0) {
                    year = adapter?.getItemAtPosition(position).toString()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setUpGenderRadioButtons(dialogBinding:DialogEditProfileBinding) {
        dialogBinding.rbMaleEditProfile.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                gender = "Male"
            }
        }

        dialogBinding.rbFemaleEditProfile.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                gender = "Female"
            }
        }

        dialogBinding.rbOtherEditProfile.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                gender = "Other"
            }
        }
    }

    override fun showError(error: String) {
        Toast.makeText(requireActivity(),error,Toast.LENGTH_SHORT).show()
    }
}