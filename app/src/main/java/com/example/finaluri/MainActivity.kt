package com.example.finaluri

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    lateinit var button: TextView
    lateinit var weightEditText : EditText
    lateinit var heightEditText : EditText
    lateinit var toggleButton : Switch
    lateinit var imperial : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        listener()
        toggleButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                weightEditText.setHint("lb")
                heightEditText.setHint("inch")
                imperial.setTypeface(Typeface.DEFAULT_BOLD)
                toggleButton.setTypeface(Typeface.DEFAULT)
            } else {
                weightEditText.setHint("kg")
                heightEditText.setHint("cm")
                toggleButton.setTypeface(Typeface.DEFAULT_BOLD)
                imperial.setTypeface(Typeface.DEFAULT)
            }
        }
        toggleButton.setOnClickListener(){
            weightEditText.getText().clear()
            heightEditText.getText().clear()
        }

    }
    private fun calculateBmi(weightInPounds: Double, heightInInches: Double): Double {
        return weightInPounds / (heightInInches * heightInInches) * 703
    }
    private fun listener() {
        button.setOnClickListener(){
            val weight = weightEditText.text.toString()
            val height = heightEditText.text.toString()

            val intent = Intent(this, ResultsActivity::class.java)
            if(weight.isEmpty() && height.isEmpty()) {
                weightEditText.error = "Please enter your weight"
                heightEditText.error = "Please enter your height"
            } else if (weight.isEmpty()){
                weightEditText.error = "Please enter your weight"
            } else if(height.isEmpty()){
                heightEditText.error = "Please enter your height"
            } else{
                    val heightPow = height.toFloat() * height.toFloat() / 10000
                    if(weightEditText.text.toString().toDouble() > 0 && heightEditText.text.toString().toDouble() > 0){
                        if (toggleButton.isChecked) {
                            val bmi = calculateBmi(weight.toDouble(), height.toDouble())
                            if(bmi > 50 || bmi < 10){
                                Toast.makeText(this,"Please input correct information",Toast.LENGTH_SHORT).show()
                            }else{
                                var bmi1 = ((bmi * 100.0).roundToInt() / 100.0).toString()
                                intent.putExtra("BMI", bmi1)
                                startActivity(intent)
                            }

                        } else {
                            var bmi = weight.toFloat() / heightPow
                            if(bmi > 50 || bmi < 10){
                                Toast.makeText(this,"Please input correct information",Toast.LENGTH_SHORT).show()
                            }else{
                                var bmi1 = ((bmi * 100.0).roundToInt() / 100.0).toString()
                                intent.putExtra("BMI", bmi1)
                                startActivity(intent)
                            }

                    }
                }else{
                        Toast.makeText(this,"Please input correct information",Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    private fun init(){
        button = findViewById(R.id.button1)
        weightEditText = findViewById(R.id.weight)
        heightEditText = findViewById(R.id.height)
        toggleButton = findViewById(R.id.togglebutton)
        imperial = findViewById(R.id.imperial)
    }
}

