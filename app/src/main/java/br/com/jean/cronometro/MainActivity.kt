package br.com.jean.cronometro

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private var running: Boolean = false
    private var wasRunning: Boolean = false
    private var seconds: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val btIniciar = findViewById<Button>(R.id.btIniciar) as Button
        val btParar = findViewById<Button>(R.id.btParar) as Button
        val btResetar = findViewById<Button>(R.id.btResetar) as Button

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds")
            running = savedInstanceState.getBoolean("running")
            wasRunning = savedInstanceState.getBoolean("wasRunning")
        }
        runTimer()

        btIniciar.setOnClickListener {
            running = true
        }

        btParar.setOnClickListener {
            running = false
        }

        btResetar.setOnClickListener {
            running = false
            seconds = 0
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

        outState.putInt("seconds", seconds)
        outState.putBoolean("running", running)
        outState.putBoolean("wasRunning", wasRunning)
    }


    override fun onPause() {
        super.onPause()
        running = false
        wasRunning = false
    }

    override fun onResume() {
        super.onResume()

        if (wasRunning)
            running = true
    }


    private fun runTimer() {
        val tvCronometro = findViewById<TextView>(R.id.tvCronometro)
        var handler: Handler = Handler(Looper.getMainLooper())
        var runnable: Runnable = Runnable { }

        runnable = object : Runnable {
            override fun run() {
                var hour: Int = seconds / 3600
                var minutes: Int = (seconds % 3600) / 60
                var secs = seconds % 60

                var time: String =
                    String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, minutes, secs)
                tvCronometro.text = time

                if (running)
                    seconds++

                handler.postDelayed(this, 1000)
            }
        }
        handler.postDelayed(runnable, 0)
    }


}