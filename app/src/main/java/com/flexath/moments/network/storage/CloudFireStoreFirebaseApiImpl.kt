package com.flexath.moments.network.storage

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.flexath.moments.data.vos.MomentVO
import com.flexath.moments.data.vos.UserVO
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.UUID

@SuppressLint("StaticFieldLeak")
object CloudFireStoreFirebaseApiImpl : CloudFireStoreFirebaseApi {

    private var database: FirebaseFirestore = Firebase.firestore
    private val storageRef = FirebaseStorage.getInstance().reference

    // General
    private var mMomentImages: String = ""

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

    private fun changeBitmapToUrlString(bitmap: Bitmap): Task<Uri> {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val imageRef = storageRef.child("images/${UUID.randomUUID()}")
        val uploadTask = imageRef.putBytes(data)

        Log.i("ImageURL", uploadTask.toString())

        uploadTask.addOnFailureListener {
            Log.i("FileUpload", "File uploaded failed")
        }.addOnSuccessListener {
            Log.i("FileUpload", "File uploaded successful")
        }

        val urlTask = uploadTask.continueWithTask {
            return@continueWithTask imageRef.downloadUrl
        }
        return urlTask
    }

    override fun updateAndUploadProfileImage(bitmap: Bitmap, user: UserVO) {

        val urlTask = changeBitmapToUrlString(bitmap)

        urlTask.addOnCompleteListener {
            val imageUrl = it.result?.toString()
            Log.i("ImageURL", imageUrl.toString())
            addUser(
                UserVO(
                    userId = user.userId,
                    userName = user.userName,
                    phoneNumber = user.phoneNumber,
                    email = user.email,
                    password = user.password,
                    birthDate = user.birthDate,
                    gender = user.gender,
                    qrCode = user.userId,
                    imageUrl = imageUrl ?: ""
                )
            )
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
                        val user = UserVO(
                            id,
                            name,
                            phoneNumber,
                            email,
                            password,
                            birthDate,
                            gender,
                            qrCode,
                            imageUrl
                        )
                        userList.add(user)
                    }
                    onSuccess(userList)
                }
            }
    }

    override fun createMoment(moment: MomentVO) {
        val userMap = hashMapOf(
            "id" to moment.id,
            "user_id" to moment.userId,
            "user_name" to moment.userName,
            "user_profile_image" to moment.userProfileImage,
            "caption" to moment.caption,
            "image_url" to moment.imageUrl
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

    override fun updateAndUploadMomentImage(bitmap: Bitmap) {
        val urlTask = changeBitmapToUrlString(bitmap)

        urlTask.addOnCompleteListener {
            val imageUrl = it.result?.toString()
            mMomentImages += "$imageUrl,"
            Log.i("Ath", mMomentImages)
        }
    }

    override fun getMomentImages(): String {
        return mMomentImages
    }

    override fun clearMomentImages() {
        mMomentImages = ""
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
                        val userId = data["user_id"] as? String ?: ""
                        val userName = data["user_name"] as String
                        val userProfileImage = data["user_profile_image"] as String
                        val caption = data["caption"] as String
                        val imageUrl = data["image_url"] as String
                        val isBookmarked = data["is_bookmarked"] as? Boolean ?: false
                        val moment = MomentVO(id,userId, userName, userProfileImage, caption, imageUrl,isBookmarked)
                        momentList.add(moment)
                    }
                    onSuccess(momentList)
                }
            }
    }

    override fun createContact(scannerId:String,qrExporterId:String,contact: UserVO) {
        val userMap = hashMapOf(
            "id" to contact.userId,
            "name" to contact.userName,
            "phone_number" to contact.phoneNumber,
            "email" to contact.email,
            "password" to contact.password,
            "birth_date" to contact.birthDate,
            "gender" to contact.gender,
            "qr_code" to contact.userId,
            "image_url" to contact.imageUrl
        )

        Log.i("QrExporterId",qrExporterId)

        database.collection("users")
            .document(scannerId)
            .collection("contacts")
            .document(qrExporterId)
            .set(userMap)
            .addOnSuccessListener {
                Log.i("FirebaseCall", "Successfully Added")
            }.addOnFailureListener {
                Log.i("FirebaseCall", "Failed Added")
            }
    }

    override fun getContacts(scannerId:String,onSuccess: (users: List<UserVO>) -> Unit, onFailure: (String) -> Unit) {
        database.collection("users")
            .document(scannerId)
            .collection("contacts")
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
                        val user = UserVO(
                            id,
                            name,
                            phoneNumber,
                            email,
                            password,
                            birthDate,
                            gender,
                            qrCode,
                            imageUrl
                        )
                        userList.add(user)
                    }
                    onSuccess(userList)
                }
            }
    }

    override fun addMomentToUserBookmarked(currentUserId: String, moment: MomentVO) {
        val userMap = hashMapOf(
            "id" to moment.id,
            "user_id" to moment.userId,
            "user_name" to moment.userName,
            "user_profile_image" to moment.userProfileImage,
            "caption" to moment.caption,
            "image_url" to moment.imageUrl
        )

        database.collection("users")
            .document(currentUserId)
            .collection("moments")
            .document(moment.id)
            .set(userMap)
            .addOnSuccessListener {
                Log.i("FirebaseCall", "Successfully Added")
            }.addOnFailureListener {
                Log.i("FirebaseCall", "Failed Added")
            }
    }

    override fun deleteMomentFromUserBookmarked(currentUserId: String, momentId: String) {
        database.collection("users")
            .document(currentUserId)
            .collection("moments")
            .document(momentId)
            .delete()
            .addOnSuccessListener {
                Log.i("FirebaseCall", "Successfully deleted")
            }.addOnFailureListener {
                Log.i("FirebaseCall", "Failed deleted")
            }
    }

    override fun getMomentsFromUserBookmarked(
        currentUserId: String,
        onSuccess: (moments: List<MomentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("users")
            .document(currentUserId)
            .collection("moments")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.localizedMessage ?: "Check Internet Connection")
                } ?: run {
                    val momentList: MutableList<MomentVO> = arrayListOf()
                    val documentList = value?.documents ?: arrayListOf()
                    for (document in documentList) {
                        val data = document.data
                        val id = data?.get("id") as String
                        val userId = data["user_id"] as? String ?: ""
                        val userName = data["user_name"] as String
                        val userProfileImage = data["user_profile_image"] as String
                        val caption = data["caption"] as String
                        val imageUrl = data["image_url"] as String
                        val isBookmarked = data["is_bookmarked"] as? Boolean ?: false
                        val moment = MomentVO(id,userId, userName, userProfileImage, caption, imageUrl,isBookmarked)
                        momentList.add(moment)
                    }
                    onSuccess(momentList)
                }
            }
    }
}