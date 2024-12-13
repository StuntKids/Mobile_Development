package com.example.stuntkids.view

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.stuntkids.R
import com.example.stuntkids.fragment.ArticleFragment
import com.example.stuntkids.fragment.HomeFragment
import com.example.stuntkids.fragment.ProfileFragment
import com.example.stuntkids.view.history.HistoryActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the Toolbar as the ActionBar
        val toolbar: Toolbar = findViewById(R.id.appBar)
        setSupportActionBar(toolbar)  // Gunakan setSupportActionBar untuk mendukung ActionBar

        val historyIcon = findViewById<ImageButton>(R.id.historyIcon)
        historyIcon.setOnClickListener {
            // Handle the click action for the History Icon
            // You can launch a new activity or show a dialog here
            startActivity(Intent(this@MainActivity, HistoryActivity::class.java))
        }

        // Bottom Navigation setup
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    true
                }

                R.id.nav_article -> {
                    loadFragment(ArticleFragment())
                    true
                }

                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }

                else -> false
            }
        }

        // Set default selected item
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }
    }

    // Function to load the selected fragment
    fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
