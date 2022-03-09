package com.example.a7minuteworkoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(),TextToSpeech.OnInitListener {

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress=0
    private var exerciseTimeDuration:Long=30
    private var restTimeDuration:Long=5


    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1
    private var tts: TextToSpeech?= null
    private var player: MediaPlayer? = null
    private var exerciseAdapter: ExerciseStatusAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        val tool_exercise_activity=findViewById<Toolbar>(R.id.toolbar_exercise_activity)
        setSupportActionBar(tool_exercise_activity)

        val actionbar = supportActionBar
        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)

        }

        tool_exercise_activity.setNavigationOnClickListener{
            customDialogForBackButton()
        }
        tts= TextToSpeech(this,this)
        exerciseList = Constants.defaultExerciseList()
        setupRestview()
        setupExerciseStatusRecyckeView()


    }

    override fun onDestroy() {
        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress=0
        }
        if(exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgress=0
        }
        if (tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if (player!=null){
            player!!.stop()
        }
        super.onDestroy()
    }

    private fun setRestProgressBar(){
        val progressBar=findViewById<ProgressBar>(R.id.progressBar)

        progressBar.max = restTimeDuration.toInt()


        progressBar.progress = restProgress
        restTimer= object :CountDownTimer(restTimeDuration*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = restTimeDuration.toInt()-restProgress
                findViewById<TextView>(R.id.tvTimer).text= (restTimeDuration-restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setupExerciseview()

            }
        }.start()

    }
    private fun setupRestview(){
        try {
            player=MediaPlayer.create(applicationContext,R.raw.press_start)
            player!!.isLooping=false
            player!!.start()

        }catch (e: Exception){
            e.printStackTrace()
        }

        findViewById<LinearLayout>(R.id.llRestView).visibility = View.VISIBLE
        findViewById<LinearLayout>(R.id.lleExerciseView).visibility=View.GONE
        if(restTimer!=null){
            restTimer!!.cancel()
            restProgress = 0
        }
        

        findViewById<TextView>(R.id.tvUpcomingExercise).text = exerciseList!![currentExercisePosition+1].getName()

        setRestProgressBar()

    }
    private fun setExerciseProgressBar() {
        val exerciseProgressBar = findViewById<ProgressBar>(R.id.exerciseProgressBar)
        exerciseProgressBar.max = exerciseTimeDuration.toInt()

        exerciseProgressBar.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(exerciseTimeDuration*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                exerciseProgressBar.progress = exerciseTimeDuration.toInt() - exerciseProgress
                findViewById<TextView>(R.id.tvExerciseTimer).text = (exerciseTimeDuration - exerciseProgress).toString()
            }

            override fun onFinish() {
                if(currentExercisePosition<exerciseList?.size!! -1 ){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestview()
                }else{
                    finish()
                    val intent = Intent(this@ExerciseActivity,finishActivity::class.java)
                    startActivity(intent)


                }
            }
        }.start()
    }
    private fun setupExerciseview(){
        findViewById<LinearLayout>(R.id.llRestView).visibility= View.GONE
        findViewById<LinearLayout>(R.id.lleExerciseView).visibility= View.VISIBLE
        if(exerciseTimer!=null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        speakOut(exerciseList!![currentExercisePosition].getName())

        setExerciseProgressBar()
        findViewById<ImageView>(R.id.ivImage).setImageResource(exerciseList!![currentExercisePosition].getImage())
        findViewById<TextView>(R.id.tvExerciseName).text= exerciseList!![currentExercisePosition].getName()

    }

    override fun onInit(status: Int) {
        if(status ==TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)
            if(result==TextToSpeech.LANG_MISSING_DATA|| result== TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("tts","The language specified is not supported")
            }
        }else{
            Log.e("TTS","Initialization Failed")
        }
    }

    private fun speakOut(text: String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    private fun setupExerciseStatusRecyckeView(){
        val rvExerciseStatus:RecyclerView=findViewById<RecyclerView>(R.id.rvExerciseStatus)
        rvExerciseStatus.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,
                false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!,this)
        rvExerciseStatus.adapter = exerciseAdapter
    }

    private fun customDialogForBackButton(){
        val customDialog = Dialog(this)
        customDialog.setContentView(R.layout.dialog_custom_back_confirmation)
        customDialog.findViewById<Button>(R.id.tvYes).setOnClickListener {
            finish()
            customDialog.dismiss()

        }
        customDialog.findViewById<Button>(R.id.tvNo).setOnClickListener {
            customDialog.dismiss()

        }
        customDialog.show()




    }

    override fun onBackPressed() {
        customDialogForBackButton()

    }
}
