package com.example.justravel.activitys

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import com.example.justravel.databinding.ActivityIntroBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class IntroActivity : BaseActivity() {
    private lateinit var binding: ActivityIntroBinding
    val storage: FirebaseStorage = FirebaseStorage.getInstance()
    val storageRef: StorageReference =
        storage.getReferenceFromUrl("gs://justravel-6fd48.appspot.com").child("video_login.mp4")
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN

        )

        


        binding.videoIntro?.setVideoPath(storageRef.toString())
        binding.videoIntro?.start()


        binding.btnSignUp?.setOnClickListener {
            startActivity(Intent(this, SignUpActuvity::class.java))
        }

        binding.btnSignIn?.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}