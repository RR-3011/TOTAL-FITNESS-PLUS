package com.example.totalfitnessplus.FitnessModule

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.totalfitnessplus.R
import org.w3c.dom.Text
//import kotlinx.android.synthetic.main.activity_bmifm.*
import androidx.appcompat.widget.Toolbar;
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW" // Metric Unit View
        private const val US_UNITS_VIEW = "US_UNIT_VIEW" // US Unit View
    }

    private var currentVisibleView: String =
        METRIC_UNITS_VIEW
    private lateinit var llUsUnitsView:LinearLayout
    private lateinit var llUsUnitsHeight:LinearLayout
    private lateinit var rbMetricUnits:RadioButton
    private lateinit var rbUsUnits:RadioButton
    private lateinit var  llMetricUnitsView:LinearLayout
    private lateinit var llUnitsView: LinearLayout
    private lateinit var  llDiplayBMIResult:LinearLayout
    private lateinit var  tvYourBMI:TextView
    private lateinit var  tvBMIValue:TextView
    private lateinit var  tvBMIType:TextView
    private lateinit var tvBMIDescription:TextView
    private lateinit var etMetricUnitHeight:EditText
    private lateinit var etMetricUnitWeight:EditText
    private lateinit var etUsUnitHeightFeet:EditText
    private lateinit var etUsUnitHeightInch:EditText
    private lateinit var etUsUnitWeight:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmifm)
        val btnCalculateUnits=findViewById<Button>(R.id.btnCalculateUnits)
//        val toolbar_bmi_activity =findViewById<Toolbar>(R.id.toolbar_bmi_activity)
        val rgUnits=findViewById<RadioGroup>(R.id.rgUnits)
        etMetricUnitHeight=findViewById<EditText>(R.id.etMetricUnitHeight)
         etMetricUnitWeight=findViewById<EditText>(R.id.etMetricUnitWeight)
         etUsUnitWeight=findViewById<EditText>(R.id.etUsUnitWeight)
         etUsUnitHeightFeet=findViewById<EditText>(R.id.etUsUnitHeightFeet)
         etUsUnitHeightInch=findViewById<EditText>(R.id.etUsUnitHeightInch)



         llUsUnitsView=findViewById<LinearLayout>(R.id.llUsUnitsView)


        llUsUnitsHeight=findViewById<LinearLayout>(R.id.llUsUnitsHeight)


         rbMetricUnits =findViewById<RadioButton>(R.id.rbMetricUnits)
        rbUsUnits=findViewById<RadioButton>(R.id.rbUsUnits)

        llMetricUnitsView=findViewById<LinearLayout>(R.id.llMetricUnitsView)
        llUnitsView=findViewById<LinearLayout>(R.id.llUnitsView)
        llDiplayBMIResult=findViewById<LinearLayout>(R.id.llDiplayBMIResult)
         tvYourBMI=findViewById<TextView>(R.id.tvYourBMI)
         tvBMIValue=findViewById<TextView>(R.id.tvBMIValue)
         tvBMIType=findViewById<TextView>(R.id.tvBMIType)
         tvBMIDescription=findViewById<TextView>(R.id.tvBMIDescription)



        supportActionBar?.hide()



        makeVisibleMetricUnitsView()


        rgUnits.setOnCheckedChangeListener { radioGroup: RadioGroup, checkedId: Int ->


            if (checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUsUnitsView()
            }
        }

        // Button will calculate the input values in Metric Units
        btnCalculateUnits.setOnClickListener {
            findViewById<ImageView>(R.id.imageView_scm).visibility=View.INVISIBLE
            findViewById<TextView>(R.id.quoteTexttv_scm).visibility=View.INVISIBLE

            if (currentVisibleView.equals(METRIC_UNITS_VIEW)) {
                // The values are validated.
                if (validateMetricUnits()) {

                    // The height value in converted to float value and divided by 100 to convert it to meter.
                    val heightValue: Float = etMetricUnitHeight.text.toString().toFloat() / 100

                    // The weight value in converted to float value
                    val weightValue: Float = etMetricUnitWeight.text.toString().toFloat()

                    // BMI value is calculated in METRIC UNITS using the height and weight value.
                    val bmi = weightValue / (heightValue * heightValue)

                    displayBMIResult(bmi)
                } else {
                    Toast.makeText(
                        this@BMIActivity,
                        "Please enter valid values.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } else {


                if (validateUsUnits()) {

                    val usUnitHeightValueFeet: String =
                        etUsUnitHeightFeet.text.toString() // Height Feet value entered in EditText component.
                    val usUnitHeightValueInch: String =
                        etUsUnitHeightInch.text.toString() // Height Inch value entered in EditText component.
                    val usUnitWeightValue: Float = etUsUnitWeight.text.toString()
                        .toFloat() // Weight value entered in EditText component.


                    val heightValue =
                        usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12


                    // Reference Link : https://www.cdc.gov/healthyweight/assessing/bmi/childrens_bmi/childrens_bmi_formula.html
                    val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))

                    displayBMIResult(bmi) // Displaying the result into UI
                } else {
                    Toast.makeText(
                        this@BMIActivity,
                        "Please enter valid values.",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }


    private fun makeVisibleMetricUnitsView() {

        currentVisibleView = METRIC_UNITS_VIEW // Current View is updated here.
        llMetricUnitsView.visibility = View.VISIBLE // METRIC UNITS VIEW is Visible

        llUsUnitsView.visibility = View.GONE // US UNITS VIEW is hidden

        etMetricUnitHeight.text!!.clear() // height value is cleared if it is added.
        etMetricUnitWeight.text!!.clear() // weight value is cleared if it is added.

        tvYourBMI.visibility = View.INVISIBLE // Result is cleared and the labels are hidden
        tvBMIValue.visibility = View.INVISIBLE // Result is cleared and the labels are hidden
        tvBMIType.visibility = View.INVISIBLE // Result is cleared and the labels are hidden
        tvBMIDescription.visibility = View.INVISIBLE // Result is cleared and the labels are hidden
    }

    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNITS_VIEW // Current View is updated here.
        llMetricUnitsView.visibility = View.GONE // METRIC UNITS VIEW is hidden
        llUsUnitsView.visibility = View.VISIBLE // US UNITS VIEW is Visible

        etUsUnitWeight.text!!.clear() // weight value is cleared if it is added.
        etUsUnitHeightFeet.text!!.clear() // height feet value is cleared if it is added.
        etUsUnitHeightInch.text!!.clear() // height inch is cleared if it is added.

        tvYourBMI.visibility = View.INVISIBLE // Result is cleared and the labels are hidden
        tvBMIValue.visibility = View.INVISIBLE // Result is cleared and the labels are hidden
        tvBMIType.visibility = View.INVISIBLE // Result is cleared and the labels are hidden
        tvBMIDescription.visibility = View.INVISIBLE // Result is cleared and the labels are hidden
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (etMetricUnitWeight.text.toString().isEmpty()) {
            isValid = false
        } else if (etMetricUnitHeight.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }

    /**
     * Function is used to validate the input values for US UNITS.
     */
    private fun validateUsUnits(): Boolean {
        var isValid = true

        if (etUsUnitWeight.text.toString().isEmpty()) {
            isValid = false
        } else if (etUsUnitHeightFeet.text.toString().isEmpty()) {
            isValid = false
        } else if (etUsUnitHeightInch.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }

    /**
     * Function is used to display the result of METRIC UNITS.
     */
    private fun displayBMIResult(bmi: Float) {

        val bmiLabel: String
        val bmiDescription: String

        if (java.lang.Float.compare(bmi, 15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 15f) > 0 && java.lang.Float.compare(
                bmi,
                16f
            ) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 16f) > 0 && java.lang.Float.compare(
                bmi,
                18.5f
            ) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (java.lang.Float.compare(bmi, 18.5f) > 0 && java.lang.Float.compare(
                bmi,
                25f
            ) <= 0
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
        } else if (java.lang.Float.compare(bmi, 30f) > 0 && java.lang.Float.compare(
                bmi,
                35f
            ) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (java.lang.Float.compare(bmi, 35f) > 0 && java.lang.Float.compare(
                bmi,
                40f
            ) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        tvYourBMI.visibility = View.VISIBLE
        tvBMIValue.visibility = View.VISIBLE
        tvBMIType.visibility = View.VISIBLE
        tvBMIDescription.visibility = View.VISIBLE

        // This is used to round of the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        tvBMIValue.text = bmiValue // Value is set to TextView
        tvBMIType.text = bmiLabel // Label is set to TextView
        tvBMIDescription.text = bmiDescription // Description is set to TextView
    }
}