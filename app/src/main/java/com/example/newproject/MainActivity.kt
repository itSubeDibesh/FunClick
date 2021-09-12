package com.example.newproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var clicks = 0

    private fun reverseTimer(Seconds: Int, tv: TextView, buttonReset: Button, clickButton: Button) {
        object : CountDownTimer((Seconds * 1000 + 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = (millisUntilFinished / 1000).toInt()
                val minutes = seconds / 60
                seconds %= 60
                tv.text = String.format("%02d", minutes) + ":" + String.format("%02d", seconds)
            }

            override fun onFinish() {
                tv.text = resources.getText(R.string.timeOver)
                buttonReset.isEnabled = true
                clickButton.isEnabled = false
            }
        }.start()
    }

    private fun displayClick(clickCount: TextView) {
        clickCount.text = clicks.toString()
    }

    private fun isFirstClick(): Boolean {
        return clicks == 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val clickButton: Button = findViewById(R.id.clickable_button)
        val clickCount: TextView = findViewById(R.id.textView_clickCount)
        val timeRemaining: TextView = findViewById(R.id.textView_timeRemaining)
        val buttonReset: Button = findViewById(R.id.reset_button)

        // Disabling Reset Button on First Click
        buttonReset.isEnabled = false

        clickButton.setOnClickListener {
            clicks++
            if (this.isFirstClick()) {
                // Start Timer Count Down
                clickButton.text = resources.getText(R.string.clickable_button)
                Toast.makeText(this, resources.getText(R.string.timeStarted), Toast.LENGTH_SHORT).show()
                this.reverseTimer(30, timeRemaining, buttonReset, clickButton)
            }
            this.displayClick(clickCount)
        }

        buttonReset.setOnClickListener {
            clicks = 0
            timeRemaining.text = resources.getText(R.string.timeRemaining)
            this.displayClick(clickCount)
            clickButton.text = resources.getText(R.string.clickable_button_Start)
            clickButton.isEnabled = true
            buttonReset.isEnabled = false
        }
    }
}