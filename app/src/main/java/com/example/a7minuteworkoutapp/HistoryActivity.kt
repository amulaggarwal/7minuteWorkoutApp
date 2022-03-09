package com.example.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sevenminuteworkout.SqliteOpenHelper

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(findViewById(R.id.toolbar_history_activity))


        val actionbar=supportActionBar
        if(actionbar!=null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "HISTORY"
        }
        findViewById<Toolbar>(R.id.toolbar_history_activity).setNavigationOnClickListener {
            onBackPressed()
        }
        getAllCompletedDates()
    }
    private fun getAllCompletedDates(){
        val dbHandler = SqliteOpenHelper(this,null)
        val allCompletedDatesList=dbHandler.getAllCompletedDatesList()
        val rvhistory=findViewById<RecyclerView>(R.id.rvHistory)

        if(allCompletedDatesList.size>0){
           findViewById<TextView>(R.id.tvHistory).visibility= View.VISIBLE
            rvhistory.visibility=View.VISIBLE
            findViewById<TextView>(R.id.tvNoDataAvailable).visibility=View.GONE

            rvhistory.layoutManager=LinearLayoutManager(this)
            val historyAdapter=HIstoryAdapter(this,allCompletedDatesList)
            rvhistory.adapter=historyAdapter


        }else{
            findViewById<TextView>(R.id.tvHistory).visibility= View.GONE
            rvhistory.visibility=View.GONE
            findViewById<TextView>(R.id.tvNoDataAvailable).visibility=View.VISIBLE

        }
    }
}