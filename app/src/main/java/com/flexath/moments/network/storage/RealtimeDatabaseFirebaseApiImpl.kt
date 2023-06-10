package com.flexath.moments.network.storage

import android.graphics.Bitmap
import android.util.Log
import com.flexath.moments.data.vos.MessageVO
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.UUID

object RealtimeDatabaseFirebaseApiImpl : RealtimeFirebaseApi {

    private var database: DatabaseReference
    private var storageRef = FirebaseStorage.getInstance().reference

    init {
        val databaseUrl =
            "https://moments-3436e-default-rtdb.asia-southeast1.firebasedatabase.app"
        database = FirebaseDatabase.getInstance(databaseUrl).reference
    }

    override fun getOtp(
        onSuccess: (groceries: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.child("otp_code")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    onSuccess(snapshot.value.toString())
                }
            })
    }


    override fun sendMessage(
        senderId: String,
        receiverId: String,
        timeStamp: Long,
        message: MessageVO
    ) {
        database.child("contactsAndMessages").child(senderId).child(receiverId)
            .child(timeStamp.toString()).setValue(message)
    }

    override fun getMessages(
        senderId: String,
        receiverId: String,
        onSuccess: (messageList: List<MessageVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.child("contactsAndMessages")
            .child(senderId)
            .child(receiverId)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val messageList = arrayListOf<MessageVO>()
                    snapshot.children.forEach { dataSnapShot ->
                        dataSnapShot.getValue(MessageVO::class.java)?.let {
                            messageList.add(it)
                        }
                    }
                    onSuccess(messageList)
                }
            })
    }

    override fun uploadAndSendImage(
        bitmap: Bitmap,
        onSuccess: (file: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val imageRef = storageRef.child("images/${UUID.randomUUID()}")
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Log.i("FileUpload", "File uploaded failed")
        }.addOnSuccessListener {
            Log.i("FileUpload", "File uploaded successful")
        }

        val urlTask = uploadTask.continueWithTask {
            return@continueWithTask imageRef.downloadUrl
        }.addOnCompleteListener {
            val imageUrl = it.result?.toString()
            if (imageUrl != null) {
                onSuccess(imageUrl)
            }
        }
    }
}