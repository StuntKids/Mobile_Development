package com.example.stuntkids.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.stuntkids.R
import com.example.stuntkids.adapter.KidProfileAdapter
import com.example.stuntkids.data.repository.FirebaseRepository
import com.example.stuntkids.databinding.FragmentProfileBinding
import com.example.stuntkids.model.Kid
import com.example.stuntkids.view.KidProfileDetailActivity
import com.example.stuntkids.view.SplashActivity
import com.example.stuntkids.view.addkid.AddKidActivity
import com.example.stuntkids.view.userprofile.UserProfileActivity

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: FirebaseRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        repository = FirebaseRepository(requireActivity())
        setViews()
        setListeners()

        return root
    }

    private fun setViews() {
        binding.apply {
            val user = repository.getCurrentUser()
            profileName.text = user?.displayName ?: "User"
            profileEmail.text = user?.email ?: "user@example.com"
            Glide.with(this@ProfileFragment)
                .load(user?.photoUrl)
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_error)
                .into(profileImage)

            val kids = listOf<Kid>(
                Kid(name = "Kid 1", age = 5),
                Kid(name = "Kid 2", age = 7),
                Kid(name = "Kid 3", age = 13),
            )

            val kidsAdapter = KidProfileAdapter(kids)
            kidsAdapter.onKidClicked = { kid ->
            }

            kidsAdapter.onAddKidClicked = {
                startActivity(Intent(requireContext(), AddKidActivity::class.java))
            }
        }
    }


    private fun setListeners() {
        binding.apply {
            userProfileBox.setOnClickListener {
                val intent = Intent(requireContext(), UserProfileActivity::class.java)
                startActivity(intent)
            }

            btnLogout.setOnClickListener {
                showLogoutConfirmationDialog()
            }
        }
    }

    private fun showLogoutConfirmationDialog() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to exit?")

        // Button "Yes"
        builder.setPositiveButton("Yes") { dialog, _ ->
            dialog.dismiss()
            repository.logout()
            Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // Button "No"
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
            // Return to the ProfileFragment without additional actions
        }

        val dialog = builder.create()
        dialog.show()
    }
}
