package com.bigdecimalit.mindreact

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import java.util.*


class MainActivity : AppCompatActivity() {

    private var currentFormula: TextView? = null
    private var previousFormula: TextView? = null
    private var counter: TextView? = null

    private var gameScore = 0
    private var previousFormulaText = ""
    private lateinit var buttonsList: ArrayList<Button>
    private var correctButton: Button? = null
    private var seekBar: SeekBar? = null
    private var mAlphaAnimation: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentFormula = findViewById(R.id.current_formula)
        previousFormula = findViewById(R.id.previous_formula)
        counter = findViewById(R.id.game_score)

        buttonsList = ArrayList(
            listOf(
                findViewById(R.id.choice1),
                findViewById(R.id.choice2),
                findViewById(R.id.choice3),
                findViewById(R.id.choice4),
                findViewById(R.id.choice5),
                findViewById(R.id.choice6)
            )
        )
        seekBar = findViewById(R.id.seekBar)
        mAlphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_animation)

        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //Toast.makeText(applicationContext, "seekbar progress: $progress", Toast.LENGTH_SHORT).show()
                Calculation.setHard(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //Toast.makeText(applicationContext, "seekbar touch started!", Toast.LENGTH_SHORT).show()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //Toast.makeText(applicationContext, "seekbar touch stopped!", Toast.LENGTH_SHORT).show()
            }

        })

        setValues()
    }

    private fun startColorAnimation(view: View, correct: Boolean) {
        lateinit var colorAnim  : ValueAnimator
        lateinit var textColorAnim  : ValueAnimator
        val colorStart = ResourcesCompat.getColor(resources, R.color.purple_500, null) //without theme
        val colorRight = ResourcesCompat.getColor(resources, R.color.correct, null) //without theme
        val colorWrong = ResourcesCompat.getColor(resources, R.color.wrong, null) //without theme
        val textColorStart = ResourcesCompat.getColor(resources,androidx.appcompat.R.color.abc_secondary_text_material_light,null)

        val colorAnimAnswer = ObjectAnimator.ofInt(previousFormula as TextView, "textColor", Color.argb(5,0,0,0), Color.argb(30,0,0,0))

        if(correct){
            colorAnim = ObjectAnimator.ofInt(view, "backgroundColor", colorStart, colorRight)
            textColorAnim = ObjectAnimator.ofInt(counter as TextView, "textColor", textColorStart, colorRight)
            previousFormula?.setTextColor(Color.argb(0,0,0,0))
        }
        else{
            colorAnim = ObjectAnimator.ofInt(view, "backgroundColor", colorStart, colorWrong)
            textColorAnim = ObjectAnimator.ofInt(counter as TextView, "textColor", textColorStart, colorWrong)
            animationRunning(colorAnimAnswer, 500)
        }

        animationRunning(colorAnim, 300)
        animationRunning(textColorAnim, 300)
    }

    private fun animationRunning(animator:ValueAnimator, duration: Long){
        animator.duration = duration
        animator.setEvaluator(ArgbEvaluator())
        animator.repeatCount = 1
        animator.repeatMode = ValueAnimator.REVERSE
        animator.start()
    }

    fun choicePressed(view: View) {
        if (view == correctButton) {
            //it can be useful in a future
            //previousFormula?.startAnimation(mAlphaAnimation)
            startColorAnimation(view, true)
            gameScore++
            counter?.text = gameScore.toString()
            setValues()
        } else {
            //previousFormula?.startAnimation(mAlphaAnimation)
            startColorAnimation(view, false)
            gameScore--
            counter?.text = gameScore.toString()
            setValues()
        }
    }

    private fun setValues() {
        previousFormula?.text = previousFormulaText
        val tempArray = Calculation.calc()
        if (tempArray[2] == 1) init(tempArray[0], "+", tempArray[1])
        else if (tempArray[2] == 2) init(tempArray[0], "-", tempArray[1])
        else if (tempArray[2] == 3) init(tempArray[0], "*", tempArray[1])
    }

    private fun init(firstNum: Int, operator: String, secondNum: Int) {
        currentFormula?.text = "$firstNum $operator $secondNum = ?"
        //gets correct number of the formula
        val correctAnswer = when (operator) {
            "+" -> firstNum + secondNum
            "-" -> firstNum - secondNum
            "*" -> firstNum * secondNum
            else -> 0
        }

        previousFormulaText = "$firstNum $operator $secondNum = $correctAnswer"

        //we need shuffle ids to init to random button correct answer
        val numbers: ArrayList<Int> = ArrayList(listOf(0, 1, 2, 3, 4, 5))
        numbers.shuffle()

        //Random().nextInt(2)+1

        //choose random button from buttonsList, numbers[0] will be different number each time after shuffle
        correctButton = buttonsList[numbers[0]]
        //we need to set correct answer to correct button
        buttonsList[numbers[0]].text = correctAnswer.toString()

        //after that we need to set incorrect generated answer for other buttons
        //they don't must be the same

        //we get remaining buttons and set value to them
        //if random num is 1 its "+", 2 its "-"

        //correctAnswer + (numbers[i] + 1) because of this formula we get always different answers
        //much bigger or less then previous

        //correctAnswer + (numbers[i]) cannot be because correct answer minus 0 will be second correct answer

        for (i in 1..5) {
            if (Random().nextInt(2) + 1 == 1)
                buttonsList[numbers[i]].text = (correctAnswer + (numbers[i] + 1)).toString()
            else
                buttonsList[numbers[i]].text = (correctAnswer - (numbers[i] + 1)).toString()
        }
    }
}