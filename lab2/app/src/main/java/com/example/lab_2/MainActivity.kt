package com.example.lab_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var sumEditText: EditText
    private lateinit var yearsSpinner: Spinner
    private lateinit var startDateEditText: EditText
    private lateinit var percentsEditText: EditText
    private lateinit var periodsSpinner: Spinner
    private lateinit var refillSpinner: Spinner
    private lateinit var calculateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sumEditText = findViewById(R.id.sum)
        yearsSpinner = findViewById(R.id.years)
        percentsEditText = findViewById(R.id.percent)
        periodsSpinner = findViewById(R.id.periods)
        refillSpinner = findViewById(R.id.refill)
        calculateButton = findViewById(R.id.calculate)

        calculateButton.setOnClickListener {
            val sum = sumEditText.text.toString().toInt()
            val years = when(yearsSpinner.selectedItemId)
            {
                0L -> 1;
                else -> {1}
            }
            val percents = percentsEditText.text.toString().toInt()
            val periods = when(periodsSpinner.selectedItemId)
            {
                0L -> 12;
                else -> 12;
            }

            val result = sum * (1 + percents.toDouble()/100/periods).pow(periods*years);
            Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show()
        }
    }
}