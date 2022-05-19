package com.bigdecimalit.mindreact

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity() : AppCompatActivity() {

    private var button: Button? = null
    private var formula: TextView? = null
    private var counter: TextView? = null
    private var correctness: TextView? = null
    private var resetButton: ImageView? = null
    private var gameScore = 0
    private lateinit var buttonsList: ArrayList<Button>
    private var correctButton: Button? = null
    private var seekBar: SeekBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //button = findViewById(R.id.choice1)
        formula = findViewById(R.id.current_formula)
        counter = findViewById(R.id.game_score)
        correctness = findViewById(R.id.correctness)
        resetButton = findViewById(R.id.reset)
        buttonsList = ArrayList(listOf(findViewById(R.id.choice1), findViewById(R.id.choice2),findViewById(R.id.choice3)))
        seekBar = findViewById(R.id.seekBar)

        resetButton?.setOnClickListener {
            setValues()
        }

        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
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

    fun rightChoice(){
        gameScore++
        counter?.text = gameScore.toString()
        correctness?.text = getString(R.string.right_choice)
        correctness?.setTextColor(Color.GREEN)
        setValues()
    }

    fun choicePressed(view: View) {
        if(view == correctButton) {
            rightChoice()
        }
        else{
            setValues()
            correctness?.text = getString(R.string.wrong_choice)
            correctness?.setTextColor(Color.RED)
        }
    }

    private fun setValues(){
        val tempArray = Calculation.calc()
        if      (tempArray[2] == 1 ) init(tempArray[0], "+", tempArray[1])
        else if (tempArray[2] == 2 ) init(tempArray[0], "-", tempArray[1])
        else if (tempArray[2] == 3 ) init(tempArray[0], "*", tempArray[1])
    }

    private fun init(firstNum:Int, operator:String, secondNum:Int){
        formula?.text = "$firstNum $operator $secondNum = ?"
        val correctAnswer = when (operator){
            "+" -> firstNum + secondNum
            "-" -> firstNum - secondNum
            "*" -> firstNum * secondNum
            else -> 0
        }
        val numbers: ArrayList<Int> = ArrayList(listOf(0,1,2))
        numbers.shuffle()

        Random().nextInt(2)+1

        correctButton = buttonsList[numbers[0]]
        buttonsList[numbers[0]].text = correctAnswer.toString()
        if(Random().nextInt(2)+1 == 1)
            buttonsList[numbers[1]].text = (correctAnswer + (numbers[1] + 1)).toString()
        else
            buttonsList[numbers[1]].text = (correctAnswer - (numbers[1] + 1)).toString()
        if(Random().nextInt(2)+1 == 1)
            buttonsList[numbers[2]].text = (correctAnswer + (numbers[2] + 1)).toString()
        else
            buttonsList[numbers[2]].text = (correctAnswer - (numbers[2] + 1)).toString()
    }
}