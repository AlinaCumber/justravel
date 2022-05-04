package com.example.justravel.firebase

import User
import android.app.Activity
import android.util.Log

import com.example.justravel.activitys.MainActivity
import com.example.justravel.activitys.MyProfileActivity
import com.example.justravel.activitys.SignInActivity
import com.example.justravel.activitys.SignUpActuvity

import com.example.justravel.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class FirestoreClass {
    private val mFirestore = FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUpActuvity, userInfo: User) {

        mFirestore.collection(Constants.USERS)

            .document(getCurrentUserId())

            .set(
                hashMapOf(
                    "id" to userInfo.id,
                    "name" to userInfo.name,
                    "e-mail" to userInfo.email,
                    "imagen" to userInfo.image,
                    "token" to userInfo.fcmToken,
                    "mobile phone" to userInfo.mobile)
            )
            .addOnSuccessListener {


                activity.userRegisteredSuccess()
            }
            .addOnFailureListener { e ->
                Log.e(
                    activity.javaClass.simpleName,
                    "Error writing document",
                    e
                )
            }
    }

    fun loadUserData(activity: Activity) {

        mFirestore.collection(Constants.USERS)

            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                Log.e(activity.javaClass.simpleName, document.toString())

                val loggedInUser = document.toObject(User::class.java)!!

                when (activity) {
                    is SignInActivity -> {
                        activity.signInSuccess(loggedInUser)
                    }
                    is MainActivity -> {
                        activity.updateNavigationUserDetails(loggedInUser)
                    }
                    is MyProfileActivity -> {
                        activity.setUserDatainUI(loggedInUser)
                    }
                }
            }
            .addOnFailureListener { e ->

                //transferimos resultado
                when (activity) {
                    is SignInActivity -> {
                        activity.hideProgressDialog()
                    }
                    is MainActivity -> {
                        activity.hideProgressDialog()
                    }
                    is MyProfileActivity -> {
                        activity.hideProgressDialog()
                    }
                }
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting loggedIn user details",
                    e
                )
            }
    }

    fun getCurrentUserId(): String{
        var currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = ""
        if(currentUser != null){
            currentUserId = currentUser.uid
        }
        return currentUserId // devuelve si el usuario actual es el mismo, si no devuelve string vacio
    }

}