package com.example.justravel.activitys

import User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.justravel.R
import com.example.justravel.databinding.ActivitySignInBinding
import com.example.justravel.databinding.ActivitySignUpActuvityBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : BaseActivity() {
    lateinit var binding: ActivitySignInBinding
    private lateinit var  auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpActionBar()

        auth = FirebaseAuth.getInstance()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN

        )

        binding.btnSignInForm.setOnClickListener {
            signInUser()
        }
    }

    private fun setUpActionBar(){
        val toolbar: Toolbar = findViewById(R.id.toolbar_sign_in_activity)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        toolbar.setNavigationOnClickListener{
            onBackPressed()
        }
    }

    private fun signInUser(){
        val email = binding.etEmailSignIn.text.toString().trim()
        val password = binding.etPasswordSignIn.text.toString().trim()

        if(validateForm(email, password)){
            showProgressDialog("Please wait")
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {

                        Log.d("Sing in", "signInWithEmail:success")
                        val user = auth.currentUser
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {

                        Log.w("Sign in", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }
                }
        }

    }

    private fun validateForm( email: String, password: String) : Boolean{
        return when {

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

    fun signInSuccess(user : User){
        hideProgressDialog()
        startActivity((Intent(this, MainActivity::class.java)))
        finish()
    }


}