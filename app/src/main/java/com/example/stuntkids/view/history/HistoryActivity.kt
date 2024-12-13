package com.example.stuntkids.view.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stuntkids.R
import com.example.stuntkids.adapter.HistoryAdapter
import com.example.stuntkids.data.model.History
import com.example.stuntkids.data.model.PredictModel
import com.example.stuntkids.data.repository.Repository
import com.example.stuntkids.databinding.ActivityHistoryBinding
import com.example.stuntkids.view.stuntingdetail.StuntingDetailActivity
import com.example.stuntkids.view.stuntingresult.StuntingResultActivity
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = Repository()

        setViews()
        setupHistory()
    }

    private fun setViews() {

        binding.apply {
            btnBack.setOnClickListener { finish() }
        }
    }

    private fun setupHistory() {
        lifecycleScope.launch {
            val history = repository.getHistories()
            val historyAdapter = HistoryAdapter(history.data)
            historyAdapter.onHistoryClick = {
                val intent = Intent(this@HistoryActivity, StuntingDetailActivity::class.java)
                Log.d("TAG", it.history.result)
                intent.putExtra(StuntingResultActivity.EXTRA_RESULT, it.history.result)
                startActivity(intent)
            }

            binding.rvHistory.apply {
                adapter = historyAdapter
                layoutManager = LinearLayoutManager(this@HistoryActivity)
            }
        }
    }
}