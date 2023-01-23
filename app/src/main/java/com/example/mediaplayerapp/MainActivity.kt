package com.example.mediaplayerapp

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var seekBar: SeekBar
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    private var  mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBar = findViewById(R.id.seekBar)
        handler = Handler(Looper.getMainLooper())


        val play = findViewById<ImageView>(R.id.playbutton)
        play.setOnClickListener {
            if (mediaPlayer == null){
                mediaPlayer = MediaPlayer.create(this, R.raw.kesariya)
            }
            mediaPlayer?.start()
            initializeSeekBar()
        }

        val pause = findViewById<ImageView>(R.id.pausebutton)
        pause.setOnClickListener {
            mediaPlayer?.pause()
        }

        val stop = findViewById<ImageView>(R.id.stopbutton)
        stop.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer = null
            handler.removeCallbacks(runnable)
            seekBar.progress = 0

        }
//        val button = findViewById<Button>(R.id.playbutton)
//        button.setOnClickListener {
//            mediaPlayer.start()
//        }
    }
    private fun initializeSeekBar(){
       seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
           override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser:Boolean) {

           }

           override fun onStartTrackingTouch(p0: SeekBar?) {

           }

           override fun onStopTrackingTouch(p0: SeekBar?) {

           }

       })

        val tvplaytime = findViewById<TextView>(R.id.tvplaytime)
        val tvduetime = findViewById<TextView>(R.id.tvduetime)
        seekBar.max = mediaPlayer!!.duration
        runnable = Runnable {
            seekBar.progress = mediaPlayer!!.currentPosition
            val playtime = mediaPlayer!!.currentPosition/1000
            tvplaytime.text = "$playtime sec"
            val duration = mediaPlayer!!.duration/1000
            val duetime = duration - playtime
            tvduetime.text ="$duetime sec"


            handler.postDelayed(runnable,1000)
        }
        handler.postDelayed(runnable,1000)
    }
}