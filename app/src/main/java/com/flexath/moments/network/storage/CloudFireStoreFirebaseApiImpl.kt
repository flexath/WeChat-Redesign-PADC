package com.flexath.moments.network.storage

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import com.flexath.moments.data.vos.MomentVO
import com.flexath.moments.data.vos.UserVO
import com.google.firebase.firestore.FirebaseFirestore
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

    override fun updateAndUploadProfileImage(bitmap: Bitmap, user: UserVO) {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val data = baos.toByteArray()

        val imageRef = storageRef.child("images/${UUID.randomUUID()}")
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

    override fun getUsers(onSuccess: (users: List<UserVO>) -> Unit, onFailure: (String) -> Unit) {
        database.collection("users")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.localizedMessage ?: "Check Internet Connection")
                } ?: run {
                    val userList: MutableList<UserVO> = arrayListOf()
                    val documentList = value?.documents ?: arrayListOf()
                    for (document in documentList) {
                        val data = document.data
                        val id = data?.get("id") as String
                        val name = data["name"] as String
                        val phoneNumber = data["phone_number"] as String
                        val email = data["email"] as String
                        val password = data["password"] as String
                        val birthDate = data["birth_date"] as String
                        val gender = data["gender"] as String
                        val qrCode = data["qr_code"] as String
                        val imageUrl = data["image_url"] as String
                        val user = UserVO(id,name,phoneNumber,email, password, birthDate, gender, qrCode, imageUrl)
                        userList.add(user)
                    }
                    onSuccess(userList)
                }
            }
    }

    override fun createMoment(moment: MomentVO) {
        val userMap = hashMapOf(
            "id" to moment.id,
            "user_name" to moment.userName,
            "user_profile_image" to moment.userProfileImage,
            "caption" to moment.caption,
            "image_url" to moment.imageUrl,
        )

        database.collection("moments")
            .document(moment.id)
            .set(userMap)
            .addOnSuccessListener {
                Log.i("FirebaseCall", "Successfully Added")
            }.addOnFailureListener {
                Log.i("FirebaseCall", "Failed Added")
            }
    }

    override fun updateAndUploadMomentImage(bitmap: Bitmap, moment: MomentVO) {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val data = baos.toByteArray()

        val imageRef = storageRef.child("images/${UUID.randomUUID()}")
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
            createMoment(MomentVO(
                id = moment.id,
                userName = moment.userName,
                userProfileImage = moment.userProfileImage,
                caption = moment.caption,
                imageUrl = imageUrl ?: ""
            ))
        }
    }

    override fun getMoments(
        onSuccess: (moments: List<MomentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("moments")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.localizedMessage ?: "Check Internet Connection")
                } ?: run {
                    val momentList: MutableList<MomentVO> = arrayListOf()
                    val documentList = value?.documents ?: arrayListOf()
                    for (document in documentList) {
                        val data = document.data
                        val id = data?.get("id") as String
                        val userName = data["user_name"] as String
                        val userProfileImage = data["user_profile_image"] as String
                        val caption = data["caption"] as String
                        val imageUrl = data["image_url"] as String
                        val moment = MomentVO(id, userName, userProfileImage, caption, imageUrl)
                        momentList.add(moment)
                    }
                    onSuccess(momentList)
                }
            }
    }
}