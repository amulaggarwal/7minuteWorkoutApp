package com.example.a7minuteworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var ll_start:LinearLayout=findViewById<LinearLayout>(R.id.ll_start)

        ll_start.setOnClickListener{
            val intent = Intent(this,ExerciseActivity::class.java)
            startActivity(intent)
        }
        findViewById<LinearLayout>(R.id.llBMI).setOnClickListener {
            val intent= Intent(this,BMIActivity::class.java)
            startActivity(intent)
        }

        findViewById<LinearLayout>(R.id.llHistory).setOnClickListener {
            val intent = Intent(this,HistoryActivity::class.java)
            startActivity(intent)
        }
    }
}



