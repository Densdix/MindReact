package com.bigdecimalit.mindreact

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //button = findViewById(R.id.choice1)
        formula = findViewById(R.id.current_formula)
        counter = findViewById(R.id.game_score)
        correctness = findViewById(R.id.correctness)
        resetButton = findViewById(R.id.reset)
        buttonsList = ArrayList(listOf(findViewById(R.id.choice1), findViewById(R.id.choice2),findViewById(R.id.choice3)))

        resetButton?.setOnClickListener {
            setValues()
        }

//        button?.setOnClickListener {
//            //tempScore = counter?.text.toString().toInt()
//
//
//            if(tempScore < 10) {
//                counter?.text = (tempScore + 1).toString()
//                if (tempScore + 1 >= 10)
//                    Toast.makeText(this, "You win!", Toast.LENGTH_SHORT).show()
//            }
//            else {
//                tempScore = 1
//                counter?.text = tempScore.toString()
//            }
//
//        }


        setValues()


    }

    fun rightChoice(){
        gameScore++
        counter?.text = gameScore.toString()
        correctness?.text = "Right"
        correctness?.setTextColor(Color.GREEN)
        setValues()
    }

    fun choicePressed(view: View) {
        if(view == findViewById(R.id.choice1) && view == correctButton) {
            //Toast.makeText(this, "choice1! - Win", Toast.LENGTH_SHORT).show()
            rightChoice()
        }
        else if(view == findViewById(R.id.choice2) && view == correctButton) {
           // Toast.makeText(this, "choice2! - Win", Toast.LENGTH_SHORT).show()
            rightChoice()
        }
        else if(view == findViewById(R.id.choice3) && view == correctButton) {
            //Toast.makeText(this, "choice3! - Win", Toast.LENGTH_SHORT).show()
            rightChoice()
        }
        else{
            setValues()
            correctness?.text = "Wrong"
            correctness?.setTextColor(Color.RED)
        }
    }

    fun setValues(){
        var numbers: ArrayList<Int> = ArrayList(listOf(0,1,2))
        numbers.shuffle()
        var tempArray = Calculation.calc()
        if(tempArray[2] == 1 ) {
            formula?.text = "" + tempArray[0] + " + " + tempArray[1] + " = ?"
            var correctAnswer = tempArray[0] + tempArray[1]
            correctButton = buttonsList[numbers[0]]
            buttonsList[numbers[0]].text = correctAnswer.toString()
            buttonsList[numbers[1]].text = (correctAnswer + 1).toString()
            buttonsList[numbers[2]].text = (correctAnswer - 1).toString()

        }
        else {
            formula?.text = "" + tempArray[0] + " - " + tempArray[1] + " = ?"
            var correctAnswer = tempArray[0] - tempArray[1]
            correctButton = buttonsList[numbers[0]]
            buttonsList[numbers[0]].text = correctAnswer.toString()
            buttonsList[numbers[1]].text = (correctAnswer + 1).toString()
            buttonsList[numbers[2]].text = (correctAnswer - 1).toString()
        }
    }
}