package project.elite.chatapp.firestore

import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import project.elite.chatapp.data.AllChats
import project.elite.chatapp.data.UserInfo
import project.elite.chatapp.navigation.Collections

fun updateChatsToFirebase(
    context: Context,
    sender: String,
    time: Long,
    receiver: String,
    message: String
) {
    val profile = AllChats(
        sender, time, receiver, message
    )

    val db = FirebaseFirestore.getInstance()
    time.let {
        db.collection(Collections.AllChats.name).document(it.toString()).set(profile)
            .addOnSuccessListener {

                Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener { exception ->
                Toast.makeText(
                    context,
                    "Process Failed : " + exception.message,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
    }

}


fun updateUserInfoToFirebase(
    context: Context,
    email: String,
    name: String,
    pfp: String?
) {
    val userInfo = UserInfo(
        email, name, pfp
    )

    val db = FirebaseFirestore.getInstance()
    email.let {
        db.collection(Collections.UserInfo.name).document(it).set(userInfo)
            .addOnSuccessListener {

                Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener { exception ->
                Toast.makeText(
                    context,
                    "Process Failed : " + exception.message,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
    }

}