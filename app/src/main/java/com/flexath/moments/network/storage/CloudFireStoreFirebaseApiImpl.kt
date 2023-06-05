package com.flexath.moments.network.storage

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import com.flexath.moments.data.vos.UserVO
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.UUID

@SuppressLint("StaticFieldLeak")
object CloudFireStoreFirebaseApiImpl : FirebaseApi {

    private var database: FirebaseFirestore = Firebase.firestore
    private val storageRef = FirebaseStorage.getInstance().reference

    override fun addUser(user: UserVO) {

        val userMap = hashMapOf(
            "id" to user.userId,
            "name" to user.userName,
            "phone_number" to user.phoneNumber,
            "email" to user.email,
            "password" to user.password,
            "birth_date" to user.birthDate,
            "gender" to user.gender,
            "qr_code" to user.userId,
            "image_url" to user.imageUrl
        )

        database.collection("users")
            .document(user.userId)
            .set(userMap)
            .addOnSuccessListener {
                Log.i("FirebaseCall", "Successfully Added")
            }.addOnFailureListener {
                Log.i("FirebaseCall", "Failed Added")
            }
    }

    override fun uploadImageAndEditGrocery(bitmap: Bitmap, user: UserVO) {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val data = baos.toByteArray()

        val imageRef = storageRef.child("images/${UUID.randomUUID()}")
        Log.i("FileUpload",storageRef.child("images/${UUID.randomUUID()}").toString())
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Log.i("FileUpload","File uploaded failed")
        }.addOnSuccessListener {
            Log.i("FileUpload","File uploaded successful")

        }

        val urlTask = uploadTask.continueWithTask {
            return@continueWithTask imageRef.downloadUrl
        }.addOnCompleteListener {
            val imageUrl = it.result?.toString()
            addUser(UserVO(
                userId = user.userId,
                userName = user.userName,
                phoneNumber = user.phoneNumber,
                email = user.email,
                password = user.password,
                birthDate = user.birthDate,
                gender = user.gender,
                qrCode = user.userId,
                imageUrl = imageUrl ?: ""
            ))
        }
    }

}