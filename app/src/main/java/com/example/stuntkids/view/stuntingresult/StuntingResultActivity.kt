package com.example.stuntkids.view.stuntingresult

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.stuntkids.R
import com.example.stuntkids.data.model.PredictModel
import com.example.stuntkids.databinding.ActivityStuntingResultBinding
import com.example.stuntkids.view.stuntingdetail.StuntingDetailActivity

class StuntingResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStuntingResultBinding

    private val stuntingResult by lazy {
        PredictModel.fromRequestText(intent.getStringExtra(EXTRA_RESULT)!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStuntingResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setViews()
        setListeners()
    }

    private fun setViews() {
        binding.apply {
            tvStatus.setBackgroundColor(
                ContextCompat.getColor(
                    this@StuntingResultActivity,
                    when (stuntingResult?.result) {
                        "Stunted" -> R.color.red
                        "Normal" -> R.color.green
                        "Severly Stunted" -> R.color.red
                        "Tinggi" -> R.color.green
                        else -> R.color.gray
                    }
                )
            )
            tvStatus.text =
                StringBuilder("Status : ").append(stuntingResult?.result)
            etName.setText(stuntingResult?.name)
            etAge.setText(stuntingResult?.age.toString())
            etGender.setText(
                when (stuntingResult?.gender) {
                    0 -> "Laki-laki"
                    1 -> "Perempuan"
                    else -> "Tidak diketahui"
                }
            )
            etHeight.setText(stuntingResult?.height.toString())
        }
    }

    private fun setListeners() {
        binding.apply {
            btnBack.setOnClickListener { finish() }

            btnDetail.setOnClickListener {
                val intent =
                    Intent(this@StuntingResultActivity, StuntingDetailActivity::class.java)

                intent.putExtra(EXTRA_RESULT, stuntingResult?.toRequestText())
                startActivity(intent)
            }
        }
    }

    companion object {
        const val EXTRA_RESULT = "extra_result"
    }
}