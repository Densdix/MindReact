package com.bigdecimalit.mindreact

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import java.util.*


class SimpleMathActivity : AppCompatActivity() {

    private var currentFormula: TextView? = null
    private var previousFormula: TextView? = null
    private var counter: TextView? = null
    private var timerCounter : TextView? = null
    private var level : TextView? = null

    private var gameScore = 0

    private var previousFormulaText = ""
    private lateinit var buttonsList: ArrayList<Button>
    private var correctButton: Button? = null
    private var mAlphaAnimation: Animation? = null

    private lateinit var timer: CountDownTimer
    private var progressBar : ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_math)

        currentFormula = findViewById(R.id.current_formula)
        previousFormula = findViewById(R.id.previous_formula)
        counter = findViewById(R.id.game_score)
        timerCounter = findViewById(R.id.timer_textView)
        level = findViewById(R.id.current_level)

        progressBar = findViewById(R.id.progressBar)

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

        mAlphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_animation)

        timer = object : CountDownTimer(60000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                val secondsStr = (millisUntilFinished / 1000).toString()
                timerCounter?.text = "00:${if (secondsStr.length == 2) secondsStr else "0$secondsStr"}"
                progressBar?.rotation = 180F
                progressBar?.progress = (millisUntilFinished / 1000).toInt()

                if((millisUntilFinished / 1000).toInt() <= 20)
                    progressBar?.progressTintList = ColorStateList.valueOf(Color.parseColor("#FFFF1744"))
                else
                    progressBar?.progressTintList = ColorStateList.valueOf(Color.parseColor("#FF00E676"))
                if((millisUntilFinished / 1000).toInt() <= 5)
                    timerCounter?.startAnimation(mAlphaAnimation)

                //else
                    //progressBar?.progressTintList = defaultProgressTintList
            }

            override fun onFinish() {
                timerCounter?.text = "00:00"
                val alertDialogBuilder = AlertDialog.Builder(this@SimpleMathActivity)
                alertDialogBuilder.setTitle("Время вышло!")
                    .setMessage("Начать сначала?")
                    .setPositiveButton("OK"
                    ) { dialog, id ->
                        Toast.makeText(
                            this@SimpleMathActivity,
                            "Нажата кнопка 'OK'",
                            Toast.LENGTH_SHORT
                        ).show()
                        Calculation.setLevel(0)
                        level?.text = "Level "+Calculation.getLevel()
                        timer.cancel()
                        timer.start()
                        gameScore = 0
                        counter?.text = gameScore.toString()
                        setValues()
                    }
                    .setNegativeButton("Просмотреть рекламу и продолжить"
                    ) { dialog, id ->
                        Toast.makeText(
                            this@SimpleMathActivity,
                            "Нажата кнопка 'Отмена'",
                            Toast.LENGTH_SHORT
                        ).show()
                        timer.cancel()
                        timer.start()
                        gameScore = 0
                        counter?.text = gameScore.toString()
                        setValues()
                    }
                    .setCancelable(false)
                alertDialogBuilder.create().show()
            }

        }.start()

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

        if(gameScore>=5){
            val colorLevel = ObjectAnimator.ofInt(level as TextView, "textColor", textColorStart, Color.argb(100,224,197,22))
            animationRunning(colorLevel, 500)
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
            gameScore++
            //it can be useful in a future
            //previousFormula?.startAnimation(mAlphaAnimation)
            startColorAnimation(view, true)
            counter?.text = gameScore.toString()
            if(gameScore >= 5){
                Calculation.setLevel(Calculation.getLevel() + 1)
                level?.text = "Level "+Calculation.getLevel()
                timer.cancel()
                timer.start()
                gameScore = 0
                counter?.text = gameScore.toString()
            }
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