package com.example.stuntkids.view.userprofile

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stuntkids.R
import com.example.stuntkids.data.repository.FirebaseRepository
import com.example.stuntkids.databinding.ActivityUserProfileBinding

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var repository: FirebaseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = FirebaseRepository(this)
        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            etName.setText(repository.getCurrentUser()?.displayName ?: "")
            etEmail.setText(repository.getCurrentUser()?.email ?: "")

            btnBack.setOnClickListener { finish() }
        }
    }
}