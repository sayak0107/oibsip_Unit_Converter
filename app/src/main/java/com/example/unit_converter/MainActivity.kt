package com.example.unit_converter

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.AdapterView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var unitSpinner: Spinner
    private lateinit var unitSpinner1: Spinner

//    Change 1
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.text)
        editText = findViewById(R.id.number)
        button = findViewById(R.id.button)
        unitSpinner = findViewById(R.id.unit_spinner)
        unitSpinner1 = findViewById(R.id.unit_spinner1)


        // Set up the Spinner with the units
        ArrayAdapter.createFromResource(
            this,
            R.array.units_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            unitSpinner.adapter = adapter
        }

        // Set up the Spinner selection listener to change the hint of the EditText
        unitSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: android.view.View,
                position: Int,
                id: Long
            ) {
                val selectedUnit1 = parent.getItemAtPosition(position).toString()
                editText.hint = "Value in $selectedUnit1"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }

        }


// converted value dropdown spinner
        button.setOnClickListener {
            val s = editText.text.toString()
            if (s.isNotEmpty()) {
                try {
                    val value = s.toDouble()
                    val selectedUnit = unitSpinner1.selectedItem.toString()
                    val selectedUnit1 = unitSpinner.selectedItem.toString()
                    val result = convertUnit(value, selectedUnit)
                    textView.text = "The value in $selectedUnit is $result"
                } catch (e: Exception) {
                    Toast.makeText(this, "Error converting unit: ${e.message}", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(this, "Please enter a value", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun convertUnit(value: Double, unit: String): Any {
        return when (unit) {
            "Kilometers" -> value // If already in kg, no conversion needed
            "Meters" -> value * 1000
            "Millimeters" -> value * 1_000_000
            "Nanometers" -> value * 1_000_000_000_000
            "Miles" -> value * 0.621371
            "Yards" -> value * 1093.61
            "Feet" -> value * 3280.84
            "Inches" -> value * 39_370.1
            else -> throw IllegalArgumentException("Unsupported unit: $unit")
        }
    }

}
