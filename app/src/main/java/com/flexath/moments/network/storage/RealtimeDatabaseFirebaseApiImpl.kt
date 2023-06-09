package com.flexath.moments.network.storage

import com.flexath.moments.data.vos.MessageVO
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object RealtimeDatabaseFirebaseApiImpl : RealtimeFirebaseApi {

    private var database: DatabaseReference

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


}