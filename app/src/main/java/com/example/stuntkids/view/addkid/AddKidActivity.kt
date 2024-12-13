package com.example.stuntkids.view.addkid

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.stuntkids.databinding.ActivityAddKidBinding
import java.util.Calendar

class AddKidActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddKidBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddKidBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            btnBack.setOnClickListener { finish() }

            etDate.setOnClickListener {
                val c = Calendar.getInstance()
                val year = c[Calendar.YEAR]
                val month = c[Calendar.MONTH]
                val day = c[Calendar.DAY_OF_MONTH]

                val datePickerDialog = DatePickerDialog(
                    this@AddKidActivity,
                    { view: DatePicker?, year1: Int, monthOfYear: Int, dayOfMonth: Int ->
                        binding.etDate.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year1)
                    },
                    year, month, day
                )
                datePickerDialog.show()
            }
            btnSave.setOnClickListener { finish() }
        }
    }
}