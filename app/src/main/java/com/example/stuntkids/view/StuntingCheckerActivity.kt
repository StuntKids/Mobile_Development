package com.example.stuntkids.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.stuntkids.R
import com.example.stuntkids.data.model.PredictModel
import com.example.stuntkids.data.repository.Repository
import com.example.stuntkids.databinding.ActivityStuntingCheckerBinding
import com.example.stuntkids.model.StuntingRequest
import com.example.stuntkids.util.StuntingCheckerAnalyzer
import com.example.stuntkids.view.stuntingresult.StuntingResultActivity
import kotlinx.coroutines.launch

class StuntingCheckerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStuntingCheckerBinding
    private lateinit var repository: Repository
    private lateinit var analyzer: StuntingCheckerAnalyzer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStuntingCheckerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        analyzer = StuntingCheckerAnalyzer(this)
        repository = Repository()

        setViews()
        setListeners()
    }

    private fun setViews() {
        binding.apply {
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this@StuntingCheckerActivity,
                android.R.layout.simple_spinner_item,
                listOf("Male", "Female")
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerGender.setAdapter(adapter)
        }
    }

    private fun setListeners() {
        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }

            btnCheck.setOnClickListener {
                lifecycleScope.launch {
                    try {
                        val request = StuntingRequest(
                            etHeight.text.toString().toInt(),
                            spinnerGender.selectedItemPosition,
                            etAge.text.toString().toInt()
                        )
                        val result = analyzer.getOutput(request)
                        val predictModel = PredictModel(
                            name = etName.text.toString(),
                            age = request.age,
                            gender = request.gender,
                            height = request.height,
                            result = result
                        )
                        repository.insertPrediction(predictModel.toRequestText())
                        val intent =
                            Intent(this@StuntingCheckerActivity, StuntingResultActivity::class.java)
                        intent.putExtra(StuntingResultActivity.EXTRA_RESULT, predictModel.toRequestText())
                        startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(this@StuntingCheckerActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}