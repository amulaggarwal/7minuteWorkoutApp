package com.example.a7minuteworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    val METRIC_UNITS_VIEW="METRIC_UNIT_VIEW"
    val US_UNITS_VIEW="US_UNIT_VIEW"
    var currentVisibleView:String= METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_m_i)

        setSupportActionBar(findViewById(R.id.toolbar_bmi_activity))
        val actionbar = supportActionBar
        val etMetricUnitWeight=findViewById<EditText>(R.id.etMetricUnitWeight)
        val etMetricUnitHeight=findViewById<EditText>(R.id.etMetricUnitHeight)
        val etUSUnitHeightInches=findViewById<EditText>(R.id.etUSUnitHeightInches)
        val etUSUnitHeightFeet=findViewById<EditText>(R.id.etUSUnitHeightFeet)
        val etUsUnitWeight=findViewById<EditText>(R.id.etUsUnitWeight)
        if(actionbar!=null){
          actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "Calculate BMI"
        }
        findViewById<Toolbar>(R.id.toolbar_bmi_activity).setNavigationOnClickListener {
            onBackPressed()
        }
        val btnCalculateUnits=findViewById<Button>(R.id.btnCalculateUnits)
        btnCalculateUnits.setOnClickListener {
            if(currentVisibleView.equals(METRIC_UNITS_VIEW)){
                if(validateMetricUnits()){
                    val heightValue: Float = etMetricUnitHeight.text.toString().toFloat()/100
                    val weightValue: Float = etMetricUnitWeight.text.toString().toFloat()

                    val bmi=weightValue/(heightValue*heightValue)
                    displayBMIResult(bmi)


                }else{
                    Toast.makeText(this@BMIActivity,"Please enter valid values",Toast.LENGTH_SHORT).show()

                }
            }else {
                if(validateUSUnits()){
                    val usUnitHeightValueFeet: String=etUSUnitHeightFeet.text.toString()
                    val usUnitHeightValueInches: String=etUSUnitHeightInches.text.toString()
                    val usUnitWeightValue: Float=etUsUnitWeight.text.toString().toFloat()

                    var heightValue=usUnitHeightValueFeet.toFloat()+usUnitHeightValueFeet.toFloat()*12

                    val bmi = 703*(usUnitWeightValue/(heightValue*heightValue))
                    displayBMIResult(bmi)

                }else{
                    Toast.makeText(this@BMIActivity,"Please enter valid values",Toast.LENGTH_SHORT).show()

                }
            }
        }
        makeVisibleMetricUnitView()
        findViewById<RadioGroup>(R.id.rgUnits).setOnCheckedChangeListener { group, checkedId ->
            if(checkedId==R.id.rbMetricUnits){
                makeVisibleMetricUnitView()
            }else{
                makeVisibleUSUnitView()
            }
        }



    }
    private fun makeVisibleMetricUnitView(){
        currentVisibleView= METRIC_UNITS_VIEW
        findViewById<TextInputLayout>(R.id.tilMetricUnitWeight).visibility=View.VISIBLE
        findViewById<TextInputLayout>(R.id.tilMetricUnitHeight).visibility=View.VISIBLE


        findViewById<EditText>(R.id.etMetricUnitHeight).text!!.clear()
        findViewById<EditText>(R.id.etMetricUnitWeight).text!!.clear()


        findViewById<TextInputLayout>(R.id.tilUsUnitWeight).visibility=View.GONE
        findViewById<LinearLayout>(R.id.llUsUnitsHeight).visibility=View.GONE

        findViewById<LinearLayout>(R.id.llDisplayBMIResult).visibility=View.GONE

    }
    private fun makeVisibleUSUnitView(){
        currentVisibleView= US_UNITS_VIEW
        findViewById<TextInputLayout>(R.id.tilMetricUnitWeight).visibility=View.GONE
        findViewById<TextInputLayout>(R.id.tilMetricUnitHeight).visibility=View.GONE

        findViewById<EditText>(R.id.etUsUnitWeight).text!!.clear()
        findViewById<EditText>(R.id.etUSUnitHeightFeet).text!!.clear()
        findViewById<EditText>(R.id.etUSUnitHeightInches).text!!.clear()


        findViewById<TextInputLayout>(R.id.tilUsUnitWeight).visibility=View.VISIBLE
        findViewById<LinearLayout>(R.id.llUsUnitsHeight).visibility=View.VISIBLE


        findViewById<LinearLayout>(R.id.llDisplayBMIResult).visibility=View.GONE

    }


    private fun displayBMIResult(bmi: Float){
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0)
        {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                        bmi,
                        30f
                ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }
        val tvYourBMI=findViewById<TextView>(R.id.tvYourBMI)
        val tvBMIValue=findViewById<TextView>(R.id.tvBMIValue)
        val tvBMIType=findViewById<TextView>(R.id.tvBMIType)
        val tvBMIDescription=findViewById<TextView>(R.id.tvBMIDescription)


        findViewById<LinearLayout>(R.id.llDisplayBMIResult).visibility=View.VISIBLE
        /*tvYourBMI.visibility= View.VISIBLE
        tvBMIValue.visibility= View.VISIBLE
        tvBMIType.visibility= View.VISIBLE
        tvBMIDescription.visibility= View.VISIBLE*/

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()

        tvBMIValue.text=bmiValue
        tvBMIType.text=bmiLabel
        tvBMIDescription.text = bmiDescription







    }

    private fun validateMetricUnits():Boolean{
        var isValid= true
        val etMetricUnitWeight=findViewById<EditText>(R.id.etMetricUnitWeight)
        val etMetricUnitHeight=findViewById<EditText>(R.id.etMetricUnitHeight)
        if (etMetricUnitWeight.text.toString().isEmpty()){
            isValid=false
        }else if (etMetricUnitHeight.text.toString().isEmpty()){
            isValid=false
        }
        return isValid
    }
    private fun validateUSUnits():Boolean{
        var isValid= true
        val etUSUnitHeightInches=findViewById<EditText>(R.id.etUSUnitHeightInches)
        val etUSUnitHeightFeet=findViewById<EditText>(R.id.etUSUnitHeightFeet)
        val etUsUnitWeight=findViewById<EditText>(R.id.etUsUnitWeight)

        when {
            etUSUnitHeightFeet.text.toString().isEmpty() -> {
                isValid=false
            }
            etUSUnitHeightInches.text.toString().isEmpty() -> {
                isValid=false
            }
            etUsUnitWeight.text.toString().isEmpty() -> {
                isValid=false
            }
        }
        return isValid
    }
}