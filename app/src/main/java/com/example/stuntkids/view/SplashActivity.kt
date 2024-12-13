package com.example.stuntkids.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stuntkids.R
import com.example.stuntkids.data.repository.FirebaseRepository
import com.example.stuntkids.data.repository.Repository
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp

class SplashActivity : AppCompatActivity() {
    private lateinit var repository: FirebaseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Mengaktifkan tampilan full screen dengan edge to edge
        setContentView(R.layout.activity_splash)  // Menetapkan layout untuk splash screen
        supportActionBar?.hide()  // Menyembunyikan ActionBar
        val repository = FirebaseRepository(this)

        repository.getCurrentUser()?.let {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Menambahkan padding pada view untuk mendukung status bar dan navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Tombol Login, akan membuka LoginActivity saat ditekan
        val loginButton = findViewById<Button>(R.id.btn1)
        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Tombol Register, akan membuka RegisterActivity saat ditekan
        val registerButton = findViewById<Button>(R.id.btn2)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
