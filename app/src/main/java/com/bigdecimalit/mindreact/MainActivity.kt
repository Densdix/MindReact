package com.bigdecimalit.mindreact

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView


class MainActivity : AppCompatActivity() {

    private var mathCard : CardView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mathCard = findViewById(R.id.simple_math_card)

        mathCard?.setOnClickListener {
            val myIntent = Intent(this, SimpleMathActivity::class.java)
            startActivity(myIntent)
        }

    }
}


