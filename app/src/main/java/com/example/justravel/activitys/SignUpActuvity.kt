package com.example.justravel.activitys

import User
import android.R.attr
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.justravel.R
import com.example.justravel.databinding.ActivityIntroBinding
import com.example.justravel.databinding.ActivitySignUpActuvityBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult

import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener

import android.R.attr.password
import com.example.justravel.firebase.FirestoreClass


class SignUpActuvity : BaseActivity() {

    lateinit var binding: ActivitySignUpActuvityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpActuvityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpActionBar()
        FirebaseApp.initializeApp(this)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN

        )


    }

    private fun setUpActionBar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_sign_up_activity)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        this.binding.btnSignUpForm.setOnClickListener {
            registerUser()
        }


    }

    fun userRegisteredSuccess() {

        Toast.makeText(
            this@SignUpActuvity,
            "You have successfully registered.",
            Toast.LENGTH_SHORT
        ).show()

        hideProgressDialog()


        FirebaseAuth.getInstance().signOut()

        finish()
    }

    private fun registerUser() {
        val name = binding.etName.text.toString().trim();
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim();

        if (validateForm(name, email, password)) {

            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        hideProgressDialog()


                        if (task.isSuccessful) {
                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            val registeredEmail = firebaseUser.email!!
                            val user = User(firebaseUser.uid, name, email)
                            FirestoreClass().registerUser(this, user)

                            Toast.makeText(
                                this@SignUpActuvity,
                                "$name you have successfully registered with email id $registeredEmail.",
                                Toast.LENGTH_SHORT
                            ).show()

                            FirebaseAuth.getInstance().signOut()
                            finish()

                        } else {
                            Toast.makeText(
                                this@SignUpActuvity,
                                task.exception!!.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
        }
    }

    private fun validateForm(name: String, email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(name) -> {
                showErrorSnackBar("Please enter a name")
                false
            }
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter a email")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter a password")
                false
            }
            else -> {
                true
            }

        }
    }
}