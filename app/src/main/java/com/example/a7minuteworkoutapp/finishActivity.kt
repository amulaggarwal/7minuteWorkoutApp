package com.example.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toolbar
import com.sevenminuteworkout.SqliteOpenHelper
import java.text.SimpleDateFormat
import java.util.*

class finishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)
        val finish_toolbar=findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_finish_activity)
        setSupportActionBar(finish_toolbar)
        val actionbar = supportActionBar
        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)

        }

        finish_toolbar.setNavigationOnClickListener{
            onBackPressed()
        }
        findViewById<Button>(R.id.btn_finish).setOnClickListener{
            finish()
        }
        addDateToDatabase()


    }
    private fun addDateToDatabase(){
        val calender= Calendar.getInstance()
        val dateTime=calender.time
        Log.i("DATE",""+dateTime)
        val sdf=SimpleDateFormat("dd MMM yyyy HH:mm:ss",Locale.getDefault())
        val date=sdf.format(dateTime)

        val dbHandler=SqliteOpenHelper(this,null)
        dbHandler.addDate(date)
        Log.i("DATE","Added")
    }

}