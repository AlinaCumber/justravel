package com.example.justravel.activitys

import User
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.justravel.R
import com.example.justravel.firebase.FirestoreClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_my_profile.*

class MyProfileActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        setupActionBar()


        FirestoreClass().loadUserData(this)
    }


    private fun setupActionBar(){
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_my_profile_activity)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar. setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.title = "My Profile"
        }

        toolbar.setNavigationOnClickListener{ onBackPressed() }

    }

    fun setUserDatainUI(user:User){
        Glide
            .with(this)
            .load(user.image)
            .centerCrop()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(iv_profile_user_image)

        et_name.setText(user.name)
        et_email.setText(user.email)
             if(user.mobile != 0L){
                 et_mobile.setText(user.mobile.toString())
            }
    }
}