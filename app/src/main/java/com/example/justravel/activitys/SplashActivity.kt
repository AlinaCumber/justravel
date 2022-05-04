package com.example.justravel.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.justravel.R
import com.example.justravel.firebase.FirestoreClass

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        supportActionBar?.hide()

        Handler().postDelayed({
            //obtenemos el usuario actual
            var currentUserId = FirestoreClass().getCurrentUserId()
            //si no esta vacio le mandamos a la actividad principal
            if(currentUserId.isNotEmpty()){
                startActivity(Intent(this, MainActivity::class.java))
            }
            //si esta vacio le mandamos a Intro para logIn o SignUp
            else{
                startActivity(Intent(this, IntroActivity::class.java))
            }


            finish()
        }, 2000)
    }
}